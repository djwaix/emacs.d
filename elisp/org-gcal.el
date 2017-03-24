(setq my-credentials-file "~/.gauth.el")
    (defun my-gSecret (_)
       (with-temp-buffer
         (insert-file-contents-literally my-credentials-file)
         (plist-get (read (buffer-string)) :"your-secret")))
(setq org-gcal-client-id "242463602627-bibg8585unk3faeem8ffq25dj0dqchu3.apps.googleusercontent.com"
      org-gcal-client-secret "your-secret"
      org-gcal-file-alist '(("thadeej@gmail.com" .  "~/todo.org")))
