;; The first three lines of this file were inserted by DrScheme. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname h06) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f ())))
;; Contract: remove-first: X (X X -> boolean) listofX -> listofX
;; Purpose:  Removes the first element with value rv from list vl.
;;           Uses eqop to determine if two elements are equal.
;; Examples: (remove-first 5 = '(12 4 6 5 7 8 9))
;;           (remove-first 'c symbol=? (list 'a 'b 'c 'd))
(define (remove-first rv eqop vl)
        (if (cons? vl)
            (if (eqop  rv
                       (first vl)
                )
                (rest vl)
                (append (list (first vl)) (remove-first rv eqop (rest vl)))
            )
            (error 'remove-first "Not a List")
        )
)
;; Test:
(check-expect (remove-first 5 = '(12 4 6 5 7 8 9)) '(12 4 6 7 8 9))
(check-expect (remove-first 'c symbol=? (list 'a 'b 'c 'd)) (list 'a 'b 'd))

;; Contract: lowoflist: listofX (X X -> X) -> X
;; Purpose:  Returns smalest element of given list,
;;           lowop is used to determine which is the
;;           lower value.
;; Examples: (lowoflist '(1 2 3 4) (lambda (x y) (if (> x y) y x)))
;;           (lowoflist '(5 3 7 4) (lambda (x y) (if (> x y) y x)))
;;(define (lowoflist vl lowop)
;;        (if (cons? vl)
;;            (local [(define (lolrecursiv vl lowop d)
;;                            (if (cons? vl)
;;                                (lolrecursiv (rest vl) lowop (lowop d (first vl)))
;;                                 d
;;                            )  
;;                   )]
;;                   (lolrecursiv (rest vl) lowop (first vl))
;;            )
;;            (error 'lowoflist "Not a List")
;;        )
;;)

;; Contract: lowoflist: listofX (X X -> X) -> X
;; Purpose:  Returns smalest element of given list,
;;           lowop is used to determine which is the
;;           lower value.
;; Examples: (lowoflist '(1 2 3 4) (lambda (x y) (if (> x y) y x)))
;;           (lowoflist '(5 3 7 4) (lambda (x y) (if (> x y) y x)))
(define (lowoflist vl lowop)
        (foldl lowop (first vl) (rest vl))
)
;; Test:
(check-expect (lowoflist '(1 2 3 4) (lambda (x y) (if (> x y) y x))) 1)
(check-expect (lowoflist '(5 3 7 4) (lambda (x y) (if (> x y) y x))) 3)

;; Contract: selection-sort: listofX (X X -> boolean) (X X -> X) -> listofX
;; Purpose:  Sorts a list via Selectionsortalgorithm.
;; Examples: (selection-sort '(1 2 3 4) = (lambda (x y) (if (> x y) y x))) '(1 2 3 4))
;;           (selection-sort '(5 3 7 1) = (lambda (x y) (if (> x y) y x))) '(1 3 5 7))
(define (selection-sort vl eqop lowop)
        (local [;;(define lol (lowoflist vl lowop))
                (define (ssrecursiv vl eqop lowop d)
                        (if (cons? vl)
                            (ssrecursiv (remove-first (lowoflist vl lowop) eqop vl) eqop lowop (append d (list (lowoflist vl lowop))))
                             d
                        )
               )]
               (ssrecursiv vl eqop lowop '())
        )
)
;; Test:
(check-expect (selection-sort '(1 2 3 4) = (lambda (x y) (if (> x y) y x))) '(1 2 3 4))
(check-expect (selection-sort '(5 3 7 1) = (lambda (x y) (if (> x y) y x))) '(1 3 5 7))
(check-expect (selection-sort (list true false false true) boolean=? (lambda (x y) (if x x y))) (list true true false false))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(define (palindrome input)
        (if (cons? input)
            (append  input
                    (rest (foldl cons
                                 empty
                                 input
                          )
                    )
            )
            ;;(error 'palindrome "Not a List")
            empty
        )
)

(check-expect (palindrome '(1 2 3 4 5 6)) '(1 2 3 4 5 6 5 4 3 2 1))
(check-expect (palindrome '(a b c d e f)) '(a b c d e f e d c b a))
(check-expect (palindrome empty) empty)
(check-expect (palindrome (list 'a 'b 'c 'd))
	      (list 'a 'b 'c 'd 'c 'b 'a))
(check-expect (palindrome empty) empty)
(check-expect (palindrome (list 1 2 3 4)) (list 1 2 3 4 3 2 1))

(define (doubled-palindrome input)
        (if (cons? input)
            (foldl (lambda (x y)
                           (append  y
                                   (cons  x
                                         (cons x
                                               empty
                                         )
                                    )
                            )
                   )
                   (list)
                   (palindrome input)
            )
            ;;(error 'palindrome-double "Not a List")
            empty
        )
)

(check-expect (doubled-palindrome '(1 2 3 4 5 6)) '(1 1 2 2 3 3 4 4 5 5 6 6 5 5 4 4 3 3 2 2 1 1))
(check-expect (doubled-palindrome '(a b c d e f)) '(a a b b c c d d e e f f e e d d c c b b a a))
(check-expect (doubled-palindrome (list 'a 'b ))
	      '(a a b b a a))
(check-expect (doubled-palindrome empty) empty)
(check-expect (doubled-palindrome (list 1 2 3))
	      (list 1 1 2 2 3 3 2 2 1 1))
                                         
                                                     
                                                     
(define-struct cargo (name urgency mass))

(define max_weight 7667) ;;in kg

;; some potential cargo items for the upcoming launch
(define chocolate (make-cargo "Chocolate" 40 5))
(define electronics (make-cargo "Entertainment Electronics" 45 100))
(define exp41 (make-cargo "Experiment #41" 50 300))
(define exp42 (make-cargo "Experiment #42" 50 1200))
(define plaques (make-cargo "Info Plaques" 60 6000))

(define potential-cargo 
  (list chocolate electronics exp41 exp42 plaques))

(define test-cargo-list1 
  (list chocolate electronics exp42 plaques))
(define test-cargo-list2 
  (list chocolate exp42 plaques))
(define test-cargo-list3 
  (list chocolate electronics exp41 exp42 plaques))

(define test_cargo_1 (list (make-cargo 'a 10 10)
                           (make-cargo 'b 10 50)
                           (make-cargo 'c 10 100)
                           (make-cargo 'd 10 150)
                           (make-cargo 'e 10 200)
                           (make-cargo 'f 10 300)
                           (make-cargo 'g 10 400)
                           (make-cargo 'h 10 500)
                           (make-cargo 'i 10 1000)
                           (make-cargo 'j 10 1500)
                           (make-cargo 'k 10 2000)
                           (make-cargo 'l 10 3000)
                      )
)

(define test_cargo_2 (list (make-cargo 'a 10 10)
                           (make-cargo 'b 20 50)
                           (make-cargo 'c 30 100)
                           (make-cargo 'd 40 150)
                           (make-cargo 'e 50 200)
                           (make-cargo 'f 100 300)
                           (make-cargo 'g 200 400)
                           (make-cargo 'h 300 500)
                           (make-cargo 'i 400 1000)
                           (make-cargo 'j 500 1500)
                           (make-cargo 'k 1000 2000)
                           (make-cargo 'l 2000 3000)
                      )
)

(define test_cargo_3 (list (make-cargo 'a 2000 10)
                           (make-cargo 'b 1000 50)
                           (make-cargo 'c 500 100)
                           (make-cargo 'd 400 150)
                           (make-cargo 'e 300 200)
                           (make-cargo 'f 200 300)
                           (make-cargo 'g 100 400)
                           (make-cargo 'h 50 500)
                           (make-cargo 'i 40 1000)
                           (make-cargo 'j 30 1500)
                           (make-cargo 'k 20 2000)
                           (make-cargo 'l 10 3000)
                      )
)

(define (cargo-urgency-sum loc)
        (if (cons? loc)
            (foldl (lambda (x y)
                           (if (cargo? x)
                               (+ y
                                  (cargo-urgency x)
                               )
                               (error 'cargo-urgency-sum "List-Element not a Cargo-Struct")
                           )
                   )
                   0
                   loc
             )
             (error 'corgo-urgency-sum "Not a List")
        )
)

(check-expect (cargo-urgency-sum test_cargo_1) 120)
(check-expect (cargo-urgency-sum test_cargo_2) 4650)
(check-expect (cargo-urgency-sum test-cargo-list1) 195)
(check-expect (cargo-urgency-sum test-cargo-list2) 150)
(check-expect (cargo-urgency-sum test-cargo-list3) 245)
(check-expect (cargo-urgency-sum potential-cargo) 245)

;;(define (cargo-urgency-mass-quotient c)
;;        (if (cargo? c)
;;            (/ (cargo-urgency c)
;;               (cargo-mass c)
;;            )
;;            (error 'corgo-urgeny-mass-quorient "Not a Cargo-Struct")
;;        )
;;)
;;(check-expect (cargo-urgency-mass-quotient chocolate) 8)
;;(check-expect (cargo-urgency-mass-quotient plaques) 0.01)

;;(define (create-cargo-list loc maxw)
;;        (local [(define (create-cargo-list-recursiv sloc amaxw)
;;                        (if (cons? sloc)
;;                            (if (<= (cargo-mass (first sloc))
;;                                     amaxw
;;                                )
;;                                (cons (first sloc)
;;                                      (create-cargo-list-recursiv (rest sloc)
;;                                                                  (- amaxw (cargo-mass (first sloc)))
;;                                      )
;;                                )
;;                               (create-cargo-list-recursiv (rest sloc)
;;                                                             amaxw
;;                                )
;;                            )
;;                            empty
;;                        )
;;               )]
;;              (create-cargo-list-recursiv (selection-sort  loc
;;                                                          (lambda (x y)
;;                                                                  (if (= (cargo-urgency-mass-quotient x)
;;                                                                         (cargo-urgency-mass-quotient y)
;;                                                                      )
;;                                                                      true
;;                                                                      false
;;                                                                  )
;;                                                          )
;;                                                          (lambda (x y)
;;                                                                  (if (> (cargo-urgency-mass-quotient x)
;;                                                                         (cargo-urgency-mass-quotient y)
;;                                                                      )
;;                                                                      x
;;                                                                      y
;;                                                                  )
;;                                                          )
;;                                          )
;;                                          maxw
;;              )
;;        )
;;)

(define (create-list cargo max-mass)
        (if (cons? cargo)
                            (if (<= (cargo-mass (first cargo))
                                     max-mass
                                )
                                (cons (first cargo)
                                      (create-list (rest cargo)
                                                                  (- max-mass (cargo-mass (first cargo)))
                                      )
                                )
                               (create-list (rest cargo)
                                                             max-mass
                                )
                            )
                            empty
                            )
)

(define (create-cargo-list-recursiv cargo cargo2 max-mass)
        (if (>= (cargo-urgency-sum (create-list cargo max-mass))
                (cargo-urgency-sum (create-list cargo2 max-mass))
            )
            (create-list cargo max-mass)
            (create-list cargo2 max-mass)
        )
)

(define (create-cargo-list cargo max-mass)
        (if (and (cons? cargo)
                 (number? max-mass)
            )
            (create-cargo-list-recursiv (selection-sort  cargo
                                                          (lambda (x y)
                                                                  (if (= (cargo-mass x)
                                                                         (cargo-mass y)
                                                                      )
                                                                      true
                                                                      false
                                                                  )
                                                          )
                                                          (lambda (x y)
                                                                  (if (> (cargo-mass x)
                                                                         (cargo-mass y)
                                                                      )
                                                                      x
                                                                      y
                                                                  )
                                                          )
                                          )
                                          (selection-sort  cargo
                                                          (lambda (y x)
                                                                  (if (= (cargo-mass x)
                                                                         (cargo-mass y)
                                                                      )
                                                                      true
                                                                      false
                                                                  )
                                                          )
                                                          (lambda (y x)
                                                                  (if (> (cargo-mass x)
                                                                         (cargo-mass y)
                                                                      )
                                                                      x
                                                                      y
                                                                  )
                                                          )
                                          )
                                          max-mass
              );;(cargolist cargo max-mass 0)
            (error 'create-cargo-list "Parameter Missmatch")
        )
)
;;(create-cargo-list test_cargo_2 max_weight)
;;(create-cargo-list test_cargo_3 max_weight)
;;(create-cargo-list potential-cargo 50)

(check-expect (create-cargo-list potential-cargo 50) 
              (list chocolate))

(check-expect (create-cargo-list test-cargo-list1 100) 
              (list electronics))
(check-expect (create-cargo-list potential-cargo 350) 
              (list exp41 chocolate))
(check-expect (create-cargo-list test-cargo-list1 350) 
              (list electronics chocolate))