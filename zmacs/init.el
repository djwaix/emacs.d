

;; Added by Package.el.  This must come before configurations of
;; installed packages.  Don't delete this line.  If you don't want it,
;; just comment it out by adding a semicolon to the start of the line.
;; You may delete these explanatory comments.
(package-initialize)

(load "~/.emacs.d/zconfig.el")

(require 'package)
(add-to-list 'package-archives
  '("melpa" . "http://melpa.milkbox.net/packages/") t)
(when (< emacs-major-version 24)
  (add-to-list 'package-archives '("gnu" . "http://elpa.gnu.org/packages/")))

;; Remedy PDF export issue
;; pdflatex command not found
;; This code should pull $PATH from user .bash_profile
(defun set-exec-path-from-shell-PATH ()
  "Sets the exec-path to the same value used by the user shell"
  (let ((path-from-shell
         (replace-regexp-in-string
          "[[:space:]\n]*$" ""
          (shell-command-to-string "$SHELL -l -c 'echo $PATH'"))))
    (setenv "PATH" path-from-shell)
    (setq exec-path (split-string path-from-shell path-separator))))

(set-exec-path-from-shell-PATH)
;; End PDF export code

(zconfig-start-benchmark)

(zconfig-load "fountain-mode")
(zconfig-load "core")
(zconfig-load "customize")
(zconfig-load "org-mode")
;; (zconfig-load "org-jira")
(zconfig-load "apel")
(if (not (iswindows)) (zconfig-load "elscreen")) ;breaks emacs-w32 on cygwin
(zconfig-load "easypg")
(zconfig-load "window-settings")
(zconfig-load "solarized-theme")
(zconfig-load "color-theme")
;; (zconfig-load "color-theme-zenburn")
(zconfig-load "ibuffer")
(zconfig-load "hippie-expand")
(zconfig-load "nxml")
;; (zconfig-load "ruby")
(zconfig-load "ruby-minimal")
(zconfig-load "auto-complete")
(zconfig-load "go-mode")
(zconfig-load "gocode")
(zconfig-load "goflymake")
(zconfig-load "python-mode")
;; (zconfig-load "pymacs")
(zconfig-load "mirah")
(zconfig-load "js2-mode")
(zconfig-load "android-mode")
;; (zconfig-load "yasnippet")
(zconfig-load "git-contrib")
(zconfig-load "egit")
(zconfig-load "magit")
(zconfig-load "mo-git-blame")
(zconfig-load "git-minimal")
(zconfig-load "twitter")
(zconfig-load "post-mode")
;; (zconfig-load "gnus")
(zconfig-load "bbdb")
(zconfig-load "puppet-mode")
(zconfig-load "markdown-mode")
;; (zconfig-load "ess")
(zconfig-load "lua-mode")
(zconfig-load "xml-rpc") ;needed by trac-wiki and jira
;; (zconfig-load "trac-wiki")
(zconfig-load "confluence-el")
;; (zconfig-load "jira")
(zconfig-load "remember")
(zconfig-load "deft")
;; (zconfig-load "paredit")
(zconfig-load "window-numbering")
;; (zconfig-load "slime")
;; (zconfig-load "clojure-mode")
(zconfig-load "cheat")
(zconfig-load "shell-current-directory")
(zconfig-load "erc")
(zconfig-load "haml-mode")
(zconfig-load "smali")
(zconfig-load "helm")
(zconfig-load "helm-git")
;; (zconfig-load "anything")
(if (not (iswindows)) (zconfig-load "w3m")) ;not important enough to solve on windows
(zconfig-load "flyspell")
(zconfig-load "rinari")
;; (zconfig-load "flex")
(zconfig-load "nav")
(zconfig-load "open-resource")
(zconfig-load "breadcrumb")
(zconfig-load "xcscope")
(zconfig-load "json") ;;required by json-pretty-print
(zconfig-load "json-pretty-print")
;; (zconfig-load "company-mode")
;; (zconfig-load "cedet")
;; (zconfig-load "ecb")
;; (zconfig-load "jdee")
;; (zconfig-load "textmate")
(zconfig-load "csharp")
(zconfig-load "undo-tree")
(zconfig-load "jabber")
(zconfig-load "ack")
(zconfig-load "csv-mode")
(zconfig-load "scss-mode")
(zconfig-load "ace-jump-mode")
(zconfig-load "erlang-mode")
(zconfig-load "rvm")
(zconfig-load "rainbow-delimiters")
;; (zconfig-load "emacs-eclim")
(zconfig-load "expand-region") ;;should come after undo-tree
(zconfig-load "multiple-cursors")
(zconfig-load "etags")
(zconfig-load "smart-tab")
(zconfig-load "ws-trim")
(zconfig-load "eshell")
(zconfig-load "tweaks")
(zconfig-load "hulu")
(zconfig-load "mu4e")
(zconfig-load "emacs-git-gutter")

;; My add-ons go here
;; (zconfig-load "tumblr")
;; (zconfig-load "org2blog")
;; (setq org2blog/wp-track-posts nil)

;;if we're running an emacs without dired-hide-details-mode
(when (not (fboundp 'dired-hide-details-mode))
  (zconfig-load "dired-details")
  (zconfig-load "dired-details-plus"))

(zconfig-load "ido")
;;(zconfig-load "el-get-setup")
(zconfig-load "smex")
;; (zconfig-load "server")
;; (zconfig-load "edit-server")

(zconfig-finish-benchmark)

(put 'narrow-to-region 'disabled nil)
(put 'set-goal-column 'disabled nil)
(put 'downcase-region 'disabled nil)

(add-to-list 'auto-mode-alist '("\\.fountain$" . fountain-mode))

(require 'package)
(add-to-list 'package-archives
             '("melpa-stable" . "http://stable.melpa.org/packages/") t)
(package-initialize)

(add-to-list 'load-path "~/.emacs.d/lisp/circe")
(require 'circe)
