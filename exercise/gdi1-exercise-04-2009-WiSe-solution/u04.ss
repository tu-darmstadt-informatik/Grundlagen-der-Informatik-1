;; The first three lines of this file were inserted by DrScheme. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname u04) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f ())))
;;multiply:number  > (number  > number)
;;returnsafunctionthattakesanumberasinputand
;;andreturnsthenumbermultipliedbyx
(define (multiply x)
  (lambda (y)(* x y))
)

(check-expect ((multiply 3) 2) 6)
(check-expect ((multiply 7) 3) 21)

(define (multiplyl x)
  (local [(define (multi y) (* x y))]
         multi
  )
)

(check-expect ((multiplyl 3) 2) 6)
(check-expect ((multiplyl 7) 3) 21)

;; Aufgabe 4.1
;; Contract: f: number number -> number
;; Aufgabe 4.2
;; Contract: g: number -> (number -> number)
;; Aufgabe 4.3/4.4

(define (addX num x)
        (+ num x)
)

(check-expect (addX 1 2) 3)
(check-expect (addX 2 2) 4)

(define (add2 num) (addX num 2))
(check-expect (add2 1) 3)
(check-expect (add2 2) 4)

(define (add3 num) (addX num 3))
(check-expect (add3 1) 4)
(check-expect (add3 2) 5)

(define (add4 num) (addX num 4))
(check-expect (add4 1) 5)
(check-expect (add4 2) 6)

;; Aufgabe 4.5

(define (make-adder x)
        (lambda (y) (+ x y))
)

(check-expect ((make-adder 1) 2) 3)
(check-expect ((make-adder 2) 2) 4)

(define (adder2 x) ((make-adder 2) x))
(check-expect (adder2 1) 3)
(check-expect (adder2 2) 4)

(define (adder3 x) ((make-adder 3) x))
(check-expect (adder3 1) 4)
(check-expect (adder3 2) 5)

(define (adder4 x) ((make-adder 4) x))
(check-expect (adder4 1) 5)
(check-expect (adder4 2) 6)

;; Aufgabe 4.6

(define (add x y) ((make-adder x) y))

(check-expect (add 1 2) 3)
(check-expect (add 2 2) 4)

;; Aufgabe 4.7

;;Contract: uncurry: (x -> (y -> z)) -> (x y -> z)
(define (uncurry x) 
        (lambda (a b) ((x a) b))
)
  
(check-expect ((uncurry make-adder) 2 2) 4)
(check-expect ((uncurry make-adder) 2 3) 5)

(define (add_ x y) ((uncurry make-adder) x y)) 

(check-expect (add_ 2 2) 4)
(check-expect (add_ 2 3) 5)

;; Aufgabe 5.1
;;Contract map: (X -> Y) (listof X) (listof X) -> (listof Y)
;;Contract map: (X -> Y) (listof X) -> (listof Y)
(check-expect (map + (list 1 2 3 4) (list 1 2 3 4)) (list 2 4 6 8))
(check-expect (map - (list 1 2 3 4) (list 1 2 3 4)) (list 0 0 0 0))
(check-expect (map (lambda (x) (+ x 2))  (list 1 2 3 4)) (list 3 4 5 6))

;; Aufgabe 5.2
(define (mymap op l)
        (if (cons? l)
            (append (list (op (first l))) (mymap op (rest l)))
            ;;(list (op (first list)))
            (list)
        )
)

(check-expect (mymap (lambda (x) (+ x 2)) (list 1 2 3)) (list 3 4 5))
(check-expect (mymap (lambda (x) (+ x 2)) (list 2 3 4)) (list 4 5 6))

;; Aufgabe 5.3/5.4

(check-expect (foldl + 4 '(1 2 3)) 10)
(check-expect (map + '(1 2 3) '(4 5 6)) '(5 7 9))

;; Aufgabe 5.5
(define (zip l1 l2)
        (map (lambda (x y) (list x y)) l1 l2)
)

(check-expect (zip '(a b c) '(1 2 3)) (list '(a 1) '(b 2) '(c 3)))
(check-expect (zip '(c b a) '(1 2 3)) (list '(c 1) '(b 2) '(a 3)))

(define (vec-multi v1 v2)
        (if (and (cons? v1)
                 (cons? v2)
            )
            (foldl + 0 (map * v1 v2))
            0
        )
)

(check-expect (vec-multi '(1 2 3) '(1 2 3)) 14)
(check-expect (vec-multi '(1 2 3 4) '(1 2 3 4)) 30)

(define (cartesian-product v1 v2)
        (if (and (cons? v1)
                 (cons? v2)
            )
            (map (lambda (x y)(foldl * x v2)) v1)
;;            (foldl + 0
;;                    (foldl (lambda (x y)(foldl * y v2)) 0 v1)
;;              )
            0
        )
)

(check-expect (cartesian-product '(1 2) '(1 2)) '(1 2 2 4))
;;(check-expect (cartesian-product '(1 2 3) '(1 2 3 4)) 36)