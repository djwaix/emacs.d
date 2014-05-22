OK. Seriously, nothing at all going on here.

Cloned ZPinter's .emacs.d repo sometime back in 2013.
https://github.com/zpinter/emacs.d

Since then, I've made absolutely minimal changes.

Just want to track those here.

Currently, I know next door to nothing about emacs.

************************************************
From Zachary Pinter's README
************************************************
This is my Emacs config, made public

Initial Setup:
Install the inconsolita font, or edit window-settings

Clone the repository to ~/.emacs.d
Edit ~/.emacs.d/init.el, choose modules as desired
Run emacs, check variable zconfig-errors if you run into any problems loading modules

Core Ideas:

A module is composed of the following:
lisp/    #auto-loaded to the path, contains .el files
info/    #auto-loaded to the info path
icons/   #auto-loaded to the image-load-path
update.el #script to update the module
init.el  #executed after load paths have been set
private.el #executed before init.el, use it to set variables for init.el that you don't want committed to the git repo

To load a module, run zconfig-load-module:
(zconfig-load "mirah")

To load multiple modules in your init.el, just do more of the same:

(zconfig-load "core")
(zconfig-load "customize")
(zconfig-load "org-mode")
(zconfig-load "org-jira")
(zconfig-load "apel")
(if (not (iswindows)) (zconfig-load "elscreen")) ;breaks emacs-w32 on cygwin
...

To launch emacs with a different set of modules:
emacs -nw -Q -l ~/.emacs_lite
