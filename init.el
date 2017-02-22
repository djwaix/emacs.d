(defun islinux ()
  (or (eq system-type "gnu/linux") (eq system-type 'gnu/linux)))

(defun ismac ()
  (or (eq system-type "darwin") (eq system-type 'darwin)))

(defun iswindows ()
  (or
   (eq system-type "cygwin")
   (eq system-type 'cygwin)
   (eq system-type "windows-nt")
   (eq system-type 'windows-nt)))

(add-to-list 'auto-mode-alist '("\\.org\\'" . org-mode))
(global-set-key "\C-cl" 'org-store-link)
(global-set-key "\C-ca" 'org-agenda)
