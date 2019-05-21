;; The first three lines of this file were inserted by DrScheme. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-abbr-reader.ss" "lang")((modname u02) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ())))
(define A (list (cons 1 empty)(list 7) 9))
(define B (cons (cons 42 empty) (list 'Hello'world'!)))

;;Contract : list-length: list >>> number
;;Purpose  : calculates the length of a list. Uses recursion
;;Example  : (list-length A)
;;           (list-length B)

;;Implementation
(define (list-length l)
        (if (empty? l) ;;if
             0 ;;if
             (if (cons? l)
                 (+ (list-length (first l)) (list-length (rest l)))
                 1
             )
        )
)

;;Test
(check-expect (list-length A) 3)
(check-expect (list-length B) 4)

;;Contract : contains?: list, symbol >>> bool
;;Purpose  : checks if symbol is in list
;;Example  : (contains? A 'Hello)
;;           (contains? B 'Hello)

;;Implementation
(define (contains? l s)
        (if (cons? l)
            (if (contains? (first l) s)
                 true
                 (contains? (rest l) s)
            )  
            (if (symbol? l)
                (if (symbol=? l s)
                    true
                    false
                )
                 false
            )
        )
)

;;Test
(check-expect (contains? A 'Hello) false)
(check-expect (contains? B 'Hello) true)


(define C (list '(a a b)))
(define D (list '(a a b) (list '(c b) (list '(c d)))))

;;Contract : remove-duplicates: list symbol >>> list
;;Purpose  : removes all duplicates in a list
;;Example  : (remove-duplicates C)
;;           (remove-duplicates D)

;;Implementation
(define (remove-duplicates l s)
        (if (= s ' )
            (if
            (
        )    
)

(define (remove-duplicate l i)
        (
)

;;Test
(check-expect (remove-duplicates C ' ) (list '(a b)))
(check-expect (remove-duplicates D ' ) (list '(a b) (list 'c (list 'd))))