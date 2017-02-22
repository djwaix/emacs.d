/*
 * Copyright (c) Eric D. Friedman 2000. All Rights Reserved.
 * Copyright (c) Paul Kinnucan 2000. All Rights Reserved.
 * Copyright (c) Charles Hart 2000. All Rights Reserved.
 *
 * $Revision: 1.6 $ 
 * $Date: 2003/09/07 05:29:12 $ 
 *
 * DelegateFactory is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2, or (at
 * your option) any later version.
 *
 * DelegateFactory is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * To obtain a copy of the GNU General Public License write to the
 * Free Software Foundation, Inc.,  59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.  
 */

package jde.wizards;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.io.PrintWriter;

/**
 * Defines a factory for generating methods that delegate tasks
 * to be performed by an instance of one class (the delegator)
 * to an instance of another class (the delegee. The delegator
 * must have a field that references the delegee.
 *
 * @author Charles Hart, Eric D. Friedman, and Paul Kinnucan
 * @version $Revision: 1.6 $
 */

public class DelegateFactory extends MethodFactory {

  /** A container for the signatures of the delegation methods
   * to be generated by this factory. */
  private SignatureContainer signatures = new SignatureContainer();

  /** The delegate factory. */
  private static DelegateFactory delegateFactory;

  /**
   * Name of the delegator field that references the delegee.
   *
   */
  private String delegee;
  
  /**
   * Creates a new <code>DelegateFactory</code> instance.
   *
   */
  public DelegateFactory() { }

  /** 
   * Creates a DelegateFactory that uses the specified NameFactory
   * for generating parameter names 
   *
   * @param factory Factory for generating parameter names
   */
  public DelegateFactory(NameFactory factory) {
    super(factory);
  }

  
  /**
   * Gets a container containing the signatures of the delegation
   * methods.
   *
   * @return the value of signatures
   */
  public SignatureContainer getSignatures()  {
    return this.signatures;
  }

  /**
   * Specifies a container to hold the signatures of the
   * delegator methods.
   *
   * @param argSignatures signature container
   */
  public void setSignatures(SignatureContainer argSignatures) {
    this.signatures = argSignatures;
  }

  /**
   * Gets the Delegate Factory
   *
   * @return the Delegate Factory
   */
  public static DelegateFactory getTheFactory()  {
    return DelegateFactory.delegateFactory;
  }

  /**
   * Sets the Delegate Factory
   *
   * @param argDelegateFactory the Delegate Factory
   */
  public static void setTheFactory(DelegateFactory argDelegateFactory) {
    DelegateFactory.delegateFactory = argDelegateFactory;
  }

  /**
   * Gets the name of the delegator field that references the delegee.
   *
   * @return name of the delegee
   */
  public String getDelegee()  {
    return this.delegee;
  }

  /**
   * Specifies the name of the delegator field that references the delegee.
   *
   * @param argDelegee the delegee
   */
  public void setDelegee(String argDelegee) {
    this.delegee = argDelegee;
  }


  /** 
   * Clears the method signatures container.
   */
  public void flush() {
    super.flush();
    signatures.clear();
  }

  
  /**
   * Generates method signatures based on introspection of the
   * specified interface. Strips package specifiers from generated
   * signatures.
   *
   * @param interfaceName the interface to process for signatures.
   * @exception ClassNotFoundException Cannot find interface 
   */
  public void process(String interfaceName)
    throws ClassNotFoundException {
    process(interfaceName, true);
  }  
  
  /**
   * Generates signatures for the public methods of the delegee
   * class. 
   *
   * @param delegeeClassName name of the delegee's class
   * @param truncate toggles truncation of package specifiers in signatures.
   *
   * @exception ClassNotFoundException the requested class cannot
   * be loaded 
   */
  public void process(String delegeeClassName, boolean truncate)
    throws ClassNotFoundException {
    
    if (null == namefactory) {
      namefactory = new DefaultNameFactory();
    }
    
    Class aclass = Class.forName(delegeeClassName);
    
    Method[] methods = aclass.getMethods();
    for (int i = 0; i < methods.length; i++) {
      if (!(methods[i].getDeclaringClass().getName().equals("java.lang.Object")
            && Modifier.isFinal(methods[i].getModifiers()))) {
        signatures.add(new Signature(methods[i], this, truncate, true));
      }
    }
  }

  /**
   * Generate delegator methods.
   *
   * @param delegeeFieldName Name of delagator field that reference
   * the delegee.
   * @param delegeeClassName  Name of delegee class.
   * @param truncate If <code>true</code>, truncate package specifier
   * when generating code.
   * 
   */
  private void makeDelegatorMethodsInternal(String delegeeFieldName,
                                            String delegeeClassName,
                                            boolean truncate) {
    delegee = delegeeFieldName;
    
    try {
      process(delegeeClassName, truncate);
    } catch (ClassNotFoundException e) {
      println("(error \"Error: could not find class named: "
              + delegeeClassName + ". "
              + "Note: name must be qualified.\")");
      return;
    } catch (Exception e) {
      println("(error \"Error: unknown type.\")");
      return;
    }

    outputMethods(new PrintWriter(System.out, true), truncate);
  }


  /**
   * Generates delegator methods.
   *
   * @param delegeeFieldName Name of delegator field that
   * references the delegee.
   * @param delegeeClassName Name of delegee's class.
   * @param truncate If <code>true</code>, truncate package specifier
   * when generating code.
   */
  public static void makeDelegatorMethods(String delegeeFieldName,
                                          String delegeeClassName, 
                                          boolean truncate) {

    if (delegateFactory == null) {
      delegateFactory = new DelegateFactory();
    }

    delegateFactory.flush();
    delegateFactory.makeDelegatorMethodsInternal(delegeeFieldName,
                                                 delegeeClassName,
                                                 truncate);
  }



  /**
   * Describe <code>getImportedClasses</code> method here.
   *
   */
  public static void getImportedClasses() {
    println(delegateFactory.getImportsAsList());
  }

  /**
   * Return a default body for the implementation of the method described
   * by <code>sig</code>.
   *
   * @param sig a <code>Signature</code> value
   * @return a <code>String</code> value
   */
  protected String getDefaultBody (Signature sig) {
    Method m = sig.getMethod();
    Class cl = m.getReturnType();
    String defBody = "";

    defBody = delegee + "." + m.getName() 
      + "(" + sig.getParameterNames() + ");";
    if (!cl.getName().equals("void")) {
      defBody = "return " + defBody;
    }
    return defBody;
  }

  /**
   * Prints delegator methods to the standard out of
   * the current process, i.e., to Emacs.
   *
   * @param out a <code>PrintWriter</code> value
   * @param truncate a <code>boolean</code> value
   */
  public void outputMethods(PrintWriter out, boolean truncate) {
    final StringBuffer buf = new StringBuffer
      ("(jde-wiz-gen-delegation-methods (list ");

    signatures.visit(new SignatureVisitor() {
        public void visit(Signature sig , boolean firstOfClass) {
          if (firstOfClass) {
            buf.append ("(quote ");
            buf.append("\"Code for delegation of ");
            buf.append(sig.getDeclaringClass().getName());
            buf.append(" methods to ");
            buf.append(delegee);
            buf.append("\")");
          }
          buf.append ("(quote ");
          buf.append(getMethodSkeletonExpression(sig));
          buf.append (")");
        }
      });
    
    buf.append("))");
    println(buf.toString());
  }
} // DelegateFactory

/*
 * $Log: DelegateFactory.java,v $
 * Revision 1.6  2003/09/07 05:29:12  paulk
 * Check for duplicate methods defined by different classes or interfaces.
 * Thanks to Martin Schwamberg.
 *
 * Revision 1.5  2002/06/06 05:12:44  paulk
 * DefaultNameFactory now generates meaningful method parameter names based
 * on the parameter type or the method name. Thanks to Ole Arndt.
 *
 * Revision 1.4  2002/05/14 06:38:44  paulk
 * Enhances code generation wizards for implementing interfaces, abstract
 * classes, etc., to use customizable templates to generate skeleton methods
 * instead of hard-wired skeletons. Thanks to "Dr. Michael Lipp" <lipp@danet.de>
 * for proposing and implementing this improvement.
 *
 * Revision 1.3  2000/08/03 04:31:20  paulk
 * Add support for generating a see secton in the Javadoc comment for a method.
 * Thanks to raffael.herzog@comartis.com
 *
 * Revision 1.2  2000/08/01 08:19:25  paulk
 * Fixes bug in dump method .Thanks to eric@hfriedman.rdsl.lmi.net.
 *
 * Revision 1.1  2000/07/14 05:26:55  paulk
 * Adds support for delegation wizard.
 *
 */

// End of DelegateFactory.java
