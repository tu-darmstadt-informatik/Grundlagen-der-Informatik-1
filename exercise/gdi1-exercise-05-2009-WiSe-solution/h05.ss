;; The first three lines of this file were inserted by DrScheme. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname h05) (read-case-sensitive #t) (teachpacks ((lib "draw.ss" "teachpack" "htdp"))) (htdp-settings #(#t constructor repeating-decimal #t #t none #f ((lib "draw.ss" "teachpack" "htdp")))))
;;(c) Ulf Gebhardt

;; Ich benutze in der ganzen Aufgabe kein (local ...)
;; Das mache ich bewusst, denn ich finde es sehr unübersichtlich,
;; wenn alle Funktion in er Prozedur definiert sind.
;; Wie man das Problem der Namensraumverschmutzung auch ohne
;; (local ...) lösen kann finden Sie am Ende der Datei (Aufgabe 5.5)

;; a bool-input is either
;; 1. a boolean value (true,false,an expression evaluated to true/false)
;; 2. a symbol 'A...'F for a boolean variable
;; 3. (list 'not b), where b is a bool-input
;; 4. (list 'and b1 b2), where b1 and b2 have type bool-input
;; 5. (list 'or b1 b2), where b1 and b2 have type bool-input

;; a bool-tree is either
;; 1. a bool-direct (boolean value or boolean variable)
;; 2. a bool-unary (unary operator, i.e., 'not)
;; 3. a bool-binary (binary operator, e.g., 'and, 'or)

;; a bool-direct represents direct boolean values
;; value: direct-boolean - a boolean value that can be either
;; 1. a boolean value, i.e., something that evaluates to true or false,
;; 2. or a symbol that represents one of the variables 'A...'F
(define-struct bool-direct (value))

;; bool-unary represents unary boolean operators
;; op: symbol - a legal unary operator (e.g., 'not)
;; param: bool-tree - a boolean expression
(define-struct bool-unary (op param))

;; bool-binary represents binary boolean operators
;; op: symbol - a legal binary operator (e.g., 'and, 'or)
;; left: bool-tree - the left (2.) part of the binary boolean expression
;; right: bool-tree - the right (3.) part of the binary boolean expr.
(define-struct bool-binary (op left right))

;; lookup-variable: symbol -> boolean
;; looks up the value of the symbol passed in
;; if undefined, returns an error
;; example: (lookup-variable 'A) is true
(define (lookup-variable variable)
  (cond
    [(symbol=? variable 'A) true]
    [(symbol=? variable 'B) false]
    [(symbol=? variable 'C) true]
    [(symbol=? variable 'D) false]
    [(symbol=? variable 'E) false]
    [(symbol=? variable 'F) true]
    [else (error 'lookup-variable 
                 (string-append "Variable "
                                (symbol->string variable)
                                " unknown"))]
    ))

;; Tests
(check-expect (lookup-variable 'A) true)
(check-expect (lookup-variable 'B) false)
(check-expect (lookup-variable 'C) true)
(check-expect (lookup-variable 'D) false)
(check-expect (lookup-variable 'E) false)
(check-expect (lookup-variable 'F) true)
(check-error  (lookup-variable 'G) "lookup-variable: Variable G unknown")


;;-------------------------------------------------------------------------------------------------------
;;-------------------------------------------CODE--------------------------------------------------------
;;-------------------------------------------------------------------------------------------------------

;; bool_not, bool_and, bool_or are constants 
;; represtenting the textual-expression of
;; the named boolean operator
;;(define bool_equal 'equal)
(define bool_not 'not)
(define bool_and 'and)
(define bool_or  'or )

;; Contract: sym_is_unary: symbol -> boolean
;; Purpose:  Tests if the given symbol sym is a unary operator
;; Examples: (sym_is_unary 'not)
;;           (sym_is_unary 'equal)
(define (sym_is_unary sym)
        (if (symbol? sym)
            (cond [(symbol=? sym bool_not) true]
                  ;;[(symbol=? sym bool_equal) true]
                  [else false]
            )
            (error 'sym_is_unary "Not a Symbol")
        )
)
;;Test
(check-expect (sym_is_unary 'not) true)
(check-expect (sym_is_unary 'equal)  false)

;; Contract: sym_is_binary: symbol -> boolean
;; Purpose:  Tests if the given symbol sym is a binary operator
;; Examples: (sym_is_binary 'and)
;;           (sym_is_binary 'equal)
(define (sym_is_binary sym)
        (cond [(symbol=? sym bool_and) true]
              [(symbol=? sym bool_or) true]
              [else false]
        )
)
;;Test
(check-expect (sym_is_binary 'and) true)
(check-expect (sym_is_binary 'equal) false)

;; Contract: input-tree: bool-input -> bool-tree
;; Purpose:  Converts a bool-input into a bool-tree
;;           Notice bool-input is not a struct, but
;;           can be many things!
;; Examples: (input->tree (list 'and 'A true)) 
;;           (input->tree (list 'or (list 'not 'A) 'B))
(define (input->tree input)
        (if (cons? input)
            (cond [(sym_is_unary (first input)) (make-bool-unary (first input) (input->tree (second input)))]
                  [(sym_is_binary (first input)) (make-bool-binary (first input) (input->tree (second input)) (input->tree (third input)))]
                  [(or (symbol? (first input)) (boolean? (first input))) (make-bool-direct (first input))]
                  [else empty] ;;empty
             )
            (if (or (symbol? input) (boolean? input))
                (make-bool-direct input)
                empty
            )
        )      
)

;; Test
(check-expect (input->tree (list 'and 'A true)) 
              (make-bool-binary 'and
                                (make-bool-direct 'A) 
                                (make-bool-direct true)))
(check-expect (input->tree (list 'or (list 'not 'A) 'B))
              (make-bool-binary 'or
                                (make-bool-unary 'not
                                                 (make-bool-direct 'A))
                                (make-bool-direct 'B)))

;; Contract: eval_not: bool -> bool
;; Purpose:  evaluates not operator on given boolean bool.
;; Errors:   "Not a Bool"
;; Examples: (eval_not true)
;;           (eval_not false)
(define (eval_not bool)
        (if (boolean? bool)
            (if bool
                false
                true
            )
            (error 'eval_not "Not a Bool")
        )
)
;; Test
(check-expect (eval_not true) false)
(check-expect (eval_not false) true)

;; Contract: eval-unary: symbol boolean -> boolean
;; Purpose:  evalutes the result of an unary-operation on given unary-param.
;; Errors:   "Not supported bool-unary"
;;           "Not bool-unary"
;; Examples: (eval-unary 'not true)
;;           (eval-unary 'not false)
(define (eval-unary unary-op unary-param)
        (if (sym_is_unary unary-op)
             (cond [(symbol=? bool_not unary-op) (eval_not unary-param)]
                   [else (error 'eval-unary "Not supported bool-unary")]
             )
             (error 'eval-unary "Not a bool-unary")
        )
)

;; Test
(check-expect (eval-unary 'not true) false)
(check-expect (eval-unary 'not false) true)

;; Contract: eval_and: boolean boolean -> boolean
;; Purpose:  evaluates and operator on given booleans bool1, bool2.
;; Errors:   "Not a Bool"
;; Examples: (eval_and true true)
;;           (eval_and false false)
(define (eval_and bool1 bool2)
        (if (and (boolean? bool1) (boolean? bool2))
            (if (and bool1 bool2)
                true
                false
            )
            (error 'eval_and "Not a Bool")
        )
)
;; Test
(check-expect (eval_and true true) true)
(check-expect (eval_and false false) false)

;; Contract: eval_or: boolean boolean -> boolean
;; Purpose:  evaluates or operator on given booleans bool1, bool2.
;; Errors:   "Not a Bool"
;; Examples: (eval_or true true)
;;           (eval_or false false)
(define (eval_or bool1 bool2)
        (if (and (boolean? bool1) (boolean? bool2))
            (if (or bool1 bool2)
                true
                false
            )
            (error 'eval_or "Not a Bool")
        )
)
;; Test
(check-expect (eval_or true true) true)
(check-expect (eval_or false false) false)

;; Contract: eval-binary: symbol boolean boolean -> boolean
;; Purpose:  evalutes the result of an binary-operation on given binary-param.
;; Error:    "Not supported Binary Operator"
;;           "Not a Binary Operator"
;; Examples: (eval-binary 'and true true)
;;           (eval-unary 'or false true)
(define (eval-binary op left right)
        (if (sym_is_binary op)
             (cond [(symbol=? bool_and op) (eval_and left right)]
                   [(symbol=? bool_or  op) (eval_or  left right)]
                   [else (error 'eval-binary "Not supported Binary Operator")]
             )
             (error 'eval-binary "Not a Binary Operator")
        )
)

;; Test
(check-expect (eval-binary 'and true true) true)
(check-expect (eval-binary 'or false true) true)

;; Contract: check_bool_tree: DATA -> boolean
;; Purpose:  Tests if given data is of type bool-tree
;;           Notice bool-tree can be many things!
;; Examples: 
(define (check_bool_tree bt)
        (if (or (bool-direct? bt)
                (bool-unary? bt)
                (bool-binary? bt)
                (boolean? bt)
            )
            true
            false
        )
)
;; Test
(check-expect (check_bool_tree true) true)
(check-expect (check_bool_tree 'abc) false)

;; Contract: eval-direct: bool-direct -> boolean
;; Purpose:  Tests if given value is a bool-direct and returns boolean value.
;;           Uses lockup-variable.
;; Error:    "Not a bool-direct"
;; Examples: (eval_direct true)
;;           (eval_direct 'B)
(define (eval-direct bd)
        (cond [(symbol? bd) (lookup-variable bd)]
              [(boolean? bd) bd]
              [else (error 'eval-direct "Not a bool-direct")]
        )
)
;; Test
(check-expect (eval-direct true) true)
(check-expect (eval-direct 'B) false)

;; Contract: bool-tree->boolean: bool-tree -> boolean
;; Purpose:  evaluates the value of a bool-tree.
;;           Uses eval-direct, eval-unary and eval-binary.
;; Errors:   "Not a bool-tree"
;; Example: (bool-tree->boolean (input->tree (list 'and 'A true)))
;;          (bool-tree->boolean (input->tree (list 'or (list 'not 'A) 'B)))         
(define (bool-tree->boolean a-bool-tree)
        (if (check_bool_tree a-bool-tree)
            (cond [(bool-direct? a-bool-tree) (eval-direct (bool-direct-value a-bool-tree))]
                  [(bool-unary? a-bool-tree)  (eval-unary  (bool-unary-op a-bool-tree)
                                                           (bool-tree->boolean (bool-unary-param a-bool-tree)))]
                  [(bool-binary? a-bool-tree) (eval-binary (bool-binary-op a-bool-tree)
                                                           (bool-tree->boolean (bool-binary-left a-bool-tree))
                                                           (bool-tree->boolean (bool-binary-right a-bool-tree)))]
                  [else (error 'bool-tree->boolean "Not a bool-tree")]
            )
            (error 'bool-tree->boolean "Not a bool-tree")
        )
)

;; Tests
(check-expect (bool-tree->boolean (input->tree (list 'and 'A true))) true)
(check-expect (bool-tree->boolean (input->tree (list 'or (list 'not 'A) 'B)))false)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

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
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Contract: bool-direct->string: bool-direct -> string
;; Purpose:  Converts a bool-direct to a string value.
;;           Notice possible values of bool-direct
;; Examples: (bool-direct->string (make-bool-direct 'A))
;;           (bool-direct->string (make-bool-direct false))
(define (bool-direct->string bd)
        (if (bool-direct? bd)
            (if (symbol? (bool-direct-value bd))
                (symbol->string (bool-direct-value bd))
                (if (bool-direct-value bd)
                    "true"
                    "false"
                )
            )
            (error 'bool-direct->string "Not a Bool-Direct")
        )
)
;; Test
(check-expect (bool-direct->string (make-bool-direct 'A)) "A")
(check-expect (bool-direct->string (make-bool-direct false)) "false")


;; Contract: bool-unary->string: bool-unary -> string
;; Purpose:  Returns a string with Bool-Unary-Operator-String
;; Examples: (bool-unary->string (make-bool-unary 'not true))
;;           (bool-unary->string (make-bool-unary 'not false))
(define (bool-unary->string bu)
        (if (bool-unary? bu)
            (symbol->string (bool-unary-op bu))
            (error 'bool-unary->string "Not a Bool-Unary")
        )
)
;; Test:
(check-expect (bool-unary->string (make-bool-unary 'not true)) "not")
(check-expect (bool-unary->string (make-bool-unary 'not false)) "not")

;; Contract: bool-binary->string: bool-binary -> string
;; Purpose:  Returns a string with Bool-Binary-Operator-String
;; Examples: (bool-binary->string (make-bool-binary 'and true true))
;;           (bool-binary->string (make-bool-binary 'or true true))
(define (bool-binary->string bb)
        (if (bool-binary? bb)
            (symbol->string (bool-binary-op bb))
            (error 'bool-unary->string "Not a Bool-Binary")
        )
)
(check-expect (bool-binary->string (make-bool-binary 'and true true)) "and")
(check-expect (bool-binary->string (make-bool-binary 'or true true)) "or")

;; Contract: tree-depth: bool-tree -> number
;; Purpose:  Calculates the depth of a bool-tree-struct
;;           First node is depth 1
;; Examples: (tree-depth (input->tree (list 'and 'A true)))
;;           (tree-depth (input->tree (list 'or (list 'not 'A) 'B)))
(define (tree-depth bt)
        (if (check_bool_tree bt)
            (cond [(bool-direct? bt) 1]
                  [(bool-unary? bt) (+ 1 (tree-depth (bool-unary-param bt)))]
                  [(bool-binary? bt) 
                   (if (> (tree-depth (bool-binary-left bt)) (tree-depth (bool-binary-right bt)))
                       (+ 1 (tree-depth (bool-binary-left bt)))
                       (+ 1 (tree-depth (bool-binary-right bt)))
                   )
                  ]
            )
            (error 'tree-depth "Not a Boo-Tree")
        )
)
;; Test:
(check-expect (tree-depth (input->tree (list 'and 'A true))) 2)
(check-expect (tree-depth (input->tree (list 'or (list 'not 'A) 'B))) 3)

;; Contract: render-tree: bool-tree number number number -> void
;; Purpose:  Renders a given bool-tree at given position number1 number2
;;           with width number3.
;; Examples: None
;;(define (render-tree bt x y d)
;;        (local [(define (render-tree-recursiv bt x y d)
;;                        (cond [(bool-direct? bt)(draw-solid-string (make-posn x y) (bool-direct->string bt))]
;;                              [(bool-unary? bt) (begin (draw-solid-string (make-posn x y) (bool-unary->string bt))
;;                                                       (draw-solid-line (make-posn x y) (make-posn (+ x d) (+ y d)))
;;                                                       (render-tree-recursiv (bool-unary-param bt) (+ x d) (+ y d) (/ d 2))
;;                                                )]
;;                              [(bool-binary? bt) (begin (draw-solid-string (make-posn x y) (bool-binary->string bt))
;;                                                       (draw-solid-line (make-posn x y) (make-posn (+ x d) (+ y d)))
;;                                                       (render-tree-recursiv (bool-binary-right bt) (+ x d) (+ y d) (/ d 2))
;;                                                       (draw-solid-line (make-posn x y) (make-posn (- x d) (+ y d)))
;;                                                       (render-tree-recursiv (bool-binary-left bt) (- x d) (+ y d) (/ d 2))
;;                                                 )]
;;                              [else void]
;;                        )
;;               )]
;;               (if (and (check_bool_tree bt)
;;                        (number? x)
;;                        (number? y)
;;                        (number? d)
;;                   )
;;                   (begin (start (+ (* d 2) x) (+ (* d 2) y))
;;                          (render-tree-recursiv bt x y d)
;;                   )
;;                   (error 'render-tree "Parameter missmatch")
;;               )
;;        )
;;)
;; Test: NONE

;;(render-tree (input->tree (list 'and 'A true)) 100 100 50)
;;(render-tree (input->tree (list 'or (list 'not 'A) 'B)) 100 100 50)
;;(render-tree (input->tree (list 'or (list 'not 'A) (list 'and 'C 'A))) 100 100 50)
;;(render-tree (input->tree (list 'or (list 'not (list 'not 'A)) (list 'and 'C 'A))) 100 100 50)
;;(render-tree (input->tree (list 'or (list 'not (list 'and (list 'not 'A) (list 'or (list 'and 'A 'B) (list 'not 'B)))) (list 'and 'C 'A))) 500 50 320)

;; Contract: determine-initial-width: bool-tree number -> number
;; Purpose:  Determines the width of the first node of the given
;;           bool-tree with given width of last node of the tree.
;; Examples: (determine-initial-width (make-bool-direct 'A) 5)
;;           (determine-initial-width (make-bool-binary 'or (make-bool-direct 'B) (make-bool-direct 'A)) 5)
(define (determine-initial-width bt d)
        (if (and (check_bool_tree bt)
                 (number? d)
            )
            (* d 
               (expt 2
                    (- (tree-depth bt)
                        1
                    )
               )
            )
            (error 'determine-initial-width "Parameter Missmatch")
        )
)
;; Test:
(check-expect (determine-initial-width (make-bool-direct 'A) 5) 5)
(check-expect (determine-initial-width (make-bool-binary 'or (make-bool-direct 'B) (make-bool-direct 'A)) 5) 10)

;; Contratc: render-tree-minimal-width: bool-tree number number number -> void
;; Purpose:  Renders a given bool-tree at given point x y with last tree-node-width
;;           equal to md.
;;           Uses determine-initial-width
;; Examples: (render-tree-minimal-width (input->tree (list 'or (list 'not (list 'and (list 'not 'A) (list 'or (list 'and 'A 'B) (list 'not 'B)))) (list 'and 'C 'A))) 500 10 10)
;;           (render-tree-minimal-width (input->tree (list 'or (list 'and (list 'and (list 'or (list 'and 'A 'B) (list 'not 'B)) (list 'not 'A)) (list 'and 'C 'A)) (list 'and 'C 'A))) 650 10 10)
(define (render-tree-minimal-width bt x y md)
        (local [(define (render-tree-inner bt x y d)
                        (local [(define (render-tree-recursiv bt x y d)
                                        (cond [(bool-direct? bt)(draw-solid-string (make-posn x y) (bool-direct->string bt))]
                                              [(bool-unary? bt) (begin (draw-solid-string (make-posn x y) (bool-unary->string bt))
                                                                       (draw-solid-line (make-posn x y) (make-posn (+ x d) (+ y d)))
                                                                       (render-tree-recursiv (bool-unary-param bt) (+ x d) (+ y d) (/ d 2))
                                              )]
                                              [(bool-binary? bt) (begin (draw-solid-string (make-posn x y) (bool-binary->string bt))
                                                                        (draw-solid-line (make-posn x y) (make-posn (+ x d) (+ y d)))
                                                                        (render-tree-recursiv (bool-binary-right bt) (+ x d) (+ y d) (/ d 2))
                                                                        (draw-solid-line (make-posn x y) (make-posn (- x d) (+ y d)))
                                                                        (render-tree-recursiv (bool-binary-left bt) (- x d) (+ y d) (/ d 2))
                                              )]
                                              [else void]
                                        )
                               )]
                               (if (and (check_bool_tree bt)
                                        (number? x)
                                        (number? y)
                                        (number? d)
                                    )
                                   (begin (start (+ (* d 2) x) (+ (* d 2) y))
                                          (render-tree-recursiv bt x y d)
                                   )
                                   (error 'render-tree "Parameter missmatch")
                               )
                        )
               )]
               (render-tree-inner bt x y (determine-initial-width bt md))
        )
)
;; Test: NONE
;;(render-tree-minimal-width (input->tree (list 'or (list 'not (list 'and (list 'not 'A) (list 'or (list 'and 'A 'B) (list 'not 'B)))) (list 'and 'C 'A))) 500 10 10)
;;(render-tree-minimal-width (input->tree (list 'or (list 'and (list 'and (list 'or (list 'and 'A 'B) (list 'not 'B)) (list 'not 'A)) (list 'and 'C 'A)) (list 'and 'C 'A))) 650 10 10)

;; Contract: render-tree-intelligent: bool-tree number -> void
;; Purpose:  Renders given bool-tree with given width for last
;;           tree-node.
;; Examples: (render-tree-intelligent (input->tree (list 'or (list 'not (list 'and (list 'not 'A) (list 'or (list 'and 'A 'B) (list 'not 'B)))) (list 'and 'C 'A))) 10)
;;           (render-tree-intelligent (input->tree (list 'or (list 'and (list 'and (list 'or (list 'and 'A 'B) (list 'not 'B)) (list 'not 'A)) (list 'and 'C 'A)) (list 'and 'C 'A))) 10)
(define (render-tree-intelligent bt md)
        (render-tree-minimal-width bt (* (determine-initial-width bt md) 2) 10 md) 
)
;; Test: NONE
;;(render-tree-intelligent (input->tree (list 'or (list 'not (list 'and (list 'not 'A) (list 'or (list 'and 'A 'B) (list 'not 'B)))) (list 'and 'C 'A))) 10)
;;(render-tree-intelligent (input->tree (list 'or (list 'and (list 'and (list 'or (list 'and 'A 'B) (list 'not 'B)) (list 'not 'A)) (list 'and 'C 'A)) (list 'and 'C 'A))) 10)

;; Contract: render-tree-moAr-intelligent: bool-tree -> void
;; Purpose:  Renders given bool-tree.
;; Examples: (render-tree-moAr-intelligent (input->tree (list 'or (list 'not (list 'and (list 'not 'A) (list 'or (list 'and 'A 'B) (list 'not 'B)))) (list 'and 'C 'A))))
;;           (render-tree-moAr-intelligent (input->tree (list 'or (list 'and (list 'and (list 'or (list 'and 'A 'B) (list 'not 'B)) (list 'not 'A)) (list 'and 'C 'A)) (list 'and 'C 'A))))
(define (render-tree-moAr-intelligent bt)
        (render-tree-minimal-width bt (* (determine-initial-width bt 10) 2) 10 10) 
)
;; Test: NONE
;;(render-tree-moAr-intelligent (input->tree (list 'or (list 'not (list 'and (list 'not 'A) (list 'or (list 'and 'A 'B) (list 'not 'B)))) (list 'and 'C 'A))))
(render-tree-moAr-intelligent (input->tree (list 'or (list 'and (list 'and (list 'or (list 'and 'A 'B) (list 'not 'B)) (list 'not 'A)) (list 'and 'C 'A)) (list 'and 'C 'A))))
(render-tree-moAr-intelligent (input->tree (list 'or (list 'and (list 'and (list 'or (list 'and 'A 'B) (list 'not 'B)) (list 'not 'A)) (list 'and 'C 'A)) (list 'and (list 'and (list 'or (list 'and 'A (list 'or 'A 'B)) 'C) 'D) 'B) 'A)))