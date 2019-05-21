;; The first three lines of this file were inserted by DrScheme. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname template-palindrome) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ())))
;; Contract: palindrome: (listof X) -> (listof X)
;; Purpose:  Generates a Palindrome of a given list
;;           alox. Last element of alox is not
;;           duplicated.
;; Examples: (palindrome '(1 2 3 4 5 6))
;;           (palindrome '(a b c d e f))
(define (palindrome alox)
        (if (cons? alox) ;;check if valid
            (append  alox ;;input
                    (rest (foldl cons ;;inverted list without last element
                                 empty
                                 alox
                          )
                    )
            )
            empty ;; No cons -> return empty
        )
)

;; Tests
(check-expect (palindrome (list 'a 'b 'c 'd))
	      (list 'a 'b 'c 'd 'c 'b 'a))
(check-expect (palindrome empty) empty)
(check-expect (palindrome (list 1 2 3 4)) (list 1 2 3 4 3 2 1))
(check-expect (palindrome '(1 2 3 4 5 6)) '(1 2 3 4 5 6 5 4 3 2 1))
(check-expect (palindrome '(a b c d e f)) '(a b c d e f e d c b a))

;; Contract: doubled-palindrome: (listof X) -> (listof X)
;; Purpose:  Generates a Palindrom of given list
;;           alox. All elements will be doubled.
;;           So every element is in list 4 times,
;;           except last element of alox, which is
;;           will be present twice, only.
;; Uses:     palindrome
;; Examples: (doubled-palindrome '(1 2 3 4 5 6))
;;           (doubled-palindrome '(a b c d e f))
(define (doubled-palindrome alox)
        (local [(define (append-double-element-to-list element list)
                        (append list ;;append to list
                                (cons element ;; 1x element
                                      (cons element ;; 2x element
                                            empty ;; nothing else
                                      )
                                )
                        )
               )]
               (if (cons? alox)
                   (foldl append-double-element-to-list
                          (list) ;;empty list as startparam
                          (palindrome alox)
                   )
                   empty
               )
        )
)

;; Tests
(check-expect (doubled-palindrome '(1 2 3 4 5 6)) '(1 1 2 2 3 3 4 4 5 5 6 6 5 5 4 4 3 3 2 2 1 1))
(check-expect (doubled-palindrome '(a b c d e f)) '(a a b b c c d d e e f f e e d d c c b b a a))
(check-expect (doubled-palindrome (list 'a 'b ))
	      '(a a b b a a))
(check-expect (doubled-palindrome empty) empty)
(check-expect (doubled-palindrome (list 1 2 3))
	      (list 1 1 2 2 3 3 2 2 1 1))
