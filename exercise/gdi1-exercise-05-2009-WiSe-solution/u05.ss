;; The first three lines of this file were inserted by DrScheme. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname u05) (read-case-sensitive #t) (teachpacks ((lib "draw.ss" "teachpack" "htdp"))) (htdp-settings #(#t constructor repeating-decimal #t #t none #f ((lib "draw.ss" "teachpack" "htdp")))))
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
        (cond [(symbol=? sym bool_not) true]
              ;;[(symbol=? sym bool_equal) true]
              [else false]
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
            (cond [(sym_is_unary (first input)) (make-bool-unary (first input) (input->tree (rest input)))]
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

;;(define (recursive-fun problem)
;; ( cond [(empty? problem) 0]
;;          [else (+ 1 (recursive-fun (rest problem)))]
;;   )
;;)

(define (trivially-solvable? p)
  (empty? p)
)

(define (determine-solution p)
  0
)

(define (combine-solutions p p2)
   (+ 1 p2)
)

(define (generate-problem p)
        (rest p)
)

(define (recursive-fun problem)
  (cond [(trivially-solvable? problem)
         (determine-solution problem)]
        [else (combine-solutions problem (recursive-fun (generate-problem problem)))]
  )
)

(check-expect (recursive-fun (list 1 2 3)) 3)
(check-expect (recursive-fun (list 1 2 3 4 5 6)) 6)

(define (calc-newton f dfx x)
        (if (= (dfx x) 0)
            0
            (- x (/ (f x) (dfx x)))
        )
)

(define (newton-method f dfx x delta)
  (if (< (- (abs x)
            (abs (calc-newton f dfx x))
         )
         (abs delta)
      )
      (calc-newton f dfx x)
      (newton-method f dfx (calc-newton f dfx x) delta)
  )
)

(check-within (newton-method (lambda (x) (* x x)) (lambda (x) (* 2 x)) -5 0.01) -0.10 0.10)
(check-within (newton-method (lambda (x) (- (* x x) 5)) (lambda (x) (* 2 x)) 1 0.01) 2.20 2.30)

;;(newton-method (lambda (x) (+ (* x x x x) -7 (* x x))) (lambda (x) (+ (* 4 x x x) (* 2 x))) 5 0.01)

;;(define (convert x b)
;;        (local [(define (convrecursiv x b c d)
;;                        (if (> c 0)
;;                            (if (- x exp(b c)
;;                                (append d 
;;                            d
;;               )]
;;               (convrecursiv x b (length-representation x b) '() )
;;)

(define (length-representation x b)
        (local [(define (rlrecursiv x b p)
                        (if (<=  x
                                (expt b p)
                            )
                            p;;if
                            (rlrecursiv x b (+ 1 p));;else
                        )
               )]
               (rlrecursiv x b 1)
        )
)

;;(length-representation 10 16)