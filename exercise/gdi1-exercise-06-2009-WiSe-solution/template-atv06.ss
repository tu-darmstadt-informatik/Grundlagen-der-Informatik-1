;; The first three lines of this file were inserted by DrScheme. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname template-atv06) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ())))
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
            empty;;(error 'remove-first "Not a List")
        )
)
;; Test:
(check-expect (remove-first 5 = '(12 4 6 5 7 8 9)) '(12 4 6 7 8 9))
(check-expect (remove-first 'c symbol=? (list 'a 'b 'c 'd)) (list 'a 'b 'd))



;; cargo represents items for transport
;; name: string - the name of the cargo item
;; urgency: number - the urgeny of the delivere,
;; the higher, the more urgend
;; mass: number - the mass of the item in kg
(define-struct cargo (name urgency mass))

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

;; Contract: cargo-urgency-sum: (listof cargo) -> number 
;; Purpose:  Returns the sum of the urgency of all
;;           Elements in cargolist.
;; Errors:   List-Element not a Cargo-Struct
;;           Not a List
;; Examples: (cargo-urgency-sum test_cargo_1)
;;           (cargo-urgency-sum test_cargo_2)
(define (cargo-urgency-sum cargolist)
        (local [(define (add-cargo-urgency-to-number cargo number)
                        (if (cargo? cargo) ;; check if it is a cargo-struct
                            (+ number
                               (cargo-urgency cargo)
                            )
                            (error 'cargo-urgency-sum "List-Element not a Cargo-Struct")
                         )
               )]
              (if (cons? cargolist) ;; check if list
                  (foldl add-cargo-urgency-to-number ;;sum-function
                         0 ;; start with 0
                         cargolist ;; iterate over whole carglist
                   )
                   0 ;;(error 'corgo-urgency-sum "Not a List")
              )
        )
)

;; Tests
(check-expect (cargo-urgency-sum test-cargo-list1) 195)
(check-expect (cargo-urgency-sum test-cargo-list2) 150)
(check-expect (cargo-urgency-sum test-cargo-list3) 245)
(check-expect (cargo-urgency-sum potential-cargo) 245)

;; Contract: cargo=? cargo cargo -> boolean
;; Purpose:  Checks if the name of two cargo-structs are
;;           the same. If this is the case true is returned
;;           else false is returned
;; Examples: (cargo=? chocolate chocolate)
;;           (cargo=? chocolate exp41)
(define (cargo=? c1 c2)
        (if (and (cargo? c1) 
                 (cargo? c2)
            )     
            (if (string=? (cargo-name c1)
                   (cargo-name c2)
                )
                true
                false
            )
            (error 'cargo=? "Not a cargo-struct")
        )
)
;; Tests:
(check-expect (cargo=? chocolate chocolate) true)
(check-expect (cargo=? chocolate exp41) false)

;; Contract: get-cargo-list-with-greater-urgency: (listof cargo) (listof cargo) -> (listof cargo)
;; Purpose:  Returns cargo-list which has more urgency.
;; Examples: (get-cargolist-with-greater-urgency (list chocolate) (list exp41))
;;           (get-cargolist-with-greater-urgency (list chocolate) (list exp42))
(define (get-cargolist-with-greater-urgency loc1 loc2)
        (if (>= (cargo-urgency-sum loc1)
                (cargo-urgency-sum loc2)
            )
            loc1
            loc2
        )
)
;; Tests:
(check-expect (get-cargolist-with-greater-urgency (list chocolate) (list exp41)) (list exp41))
(check-expect (get-cargolist-with-greater-urgency (list chocolate) (list exp42)) (list exp42))

;; Contract: create-carg-list: (listof cargo) number -> (listof cargo)
;; Purpose:  Calculates the optimal cargo-list for given max-mass.
;;           Depending on Mass and Urgency of Cargo-Items
;;           This function uses a recursion which test every possibility
;;           of cargo-list-combinations until there is no mas-left.
;;           Notice: This function jumps in its recursion between
;;                   the two local defined function. This might be
;;                   bad style, but since it makes the whole thing
;;                   a little more readable i though it would be
;;                   a good idea.
;; Example:  (create-cargo-list potential-cargo 50) 
;;           (create-cargo-list test-cargo-list1 100) 
(define (create-cargo-list cargo max-mass)
                ;;Calculates if new-cargo-element still fits in the atv(mass). If it does function calls create-cargo-list-recursiv, to check if even more items fit in.
        (local [(define (calculate-new-possible-cargo-lists rest-cargo loaded-cargo new-cargo-element rest-mass create-cargo-list-recursiv-func)
                        (if (>= rest-mass
                                0
                            ) ;;mass already calculated -> does new item fit?
                            (create-cargo-list-recursiv-func rest-cargo
                                                             rest-mass
                                                             (append (list new-cargo-element)
                                                                     loaded-cargo
                                                             );;append new-item to cargo-list
                            ) ;; check if even more items fit
                            loaded-cargo ;; New element does not match mass-requirements
                       )
               )
                
               (define (create-cargo-list-recursiv rest-cargo rest-mass loaded-cargo)
                       (if (cons? rest-cargo) ;;something left to load?
                           (foldl (lambda (new-cargo-element current-cargo-list) ;;i use fold to itterate over list and store list with greater urgency
                                          (get-cargolist-with-greater-urgency (calculate-new-possible-cargo-lists (remove-first new-cargo-element ;;remove the element
                                                                                                                                cargo=?
                                                                                                                                rest-cargo
                                                                                                                  )
                                                                                                                  loaded-cargo 
                                                                                                                  new-cargo-element
                                                                                                                  (- rest-mass ;;calculate new rest-mass
                                                                                                                     (cargo-mass new-cargo-element)
                                                                                                                  )
                                                                                                                  create-cargo-list-recursiv
                                                                               )
                                                                               current-cargo-list ;; which one has more urgency - new cargo-list or already calculated?
                                          )
                                   )
                                   (list) ;;start with empty list
                                   rest-cargo ;;fold over all cargo-elements
                            )
                            loaded-cargo ;;nothing to load left - return load-list
                        )
               )]
              (create-cargo-list-recursiv cargo ;;start recursion
                                          max-mass 
                                          (list) ;;nothing loaded yet
              )
         )
)

;; Tests
(check-expect (create-cargo-list potential-cargo 50) 
              (list chocolate))
(check-expect (create-cargo-list test-cargo-list1 100) 
              (list electronics))
(check-expect (create-cargo-list potential-cargo 350) 
              (list chocolate exp41))
(check-expect (create-cargo-list test-cargo-list1 350) 
              (list chocolate electronics))