;; The first three lines of this file were inserted by DrScheme. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname circle) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ())))
;; Exercise #01
;; (c) Ulf Gebhardt

;;Contract: make-point: number, number  >>> point
;;Purpose: Struct to store two numbers, a x and a y value.
;;Example: (make-point 1 1)
;;         (make-point 2 -1)

;;Definition:
(define-struct point(x y))

;;Contract: point_point_distance: point, point  >>> number
;;Purpose: Calculates distance between two points with sqrt(sqr(y1 - y2) + sqr(x1 - x2))
;;Example: (point_point_distance (make-point 1 1) (make-point 2 -1)) 
;;         (point_point_distance (make-point 1 5) (make-point -2 1)) 

;;Definition:
(define (point_point_distance p1 p2)
                            (sqrt (+ (sqr (- (point-y p1) (point-y p2)))
                                     (sqr (- (point-x p1) (point-x p2)))
                                  )
                            )
)
;;Test
(check-within (point_point_distance (make-point 1 1) (make-point 2 -1)) 2.23 2.24)
(check-expect (point_point_distance (make-point 1 5) (make-point -2 1)) 5)

;;Contract: make-circle: point, number  >>> circle
;;Purpose: Circle Struct, containing a point and a radius value to describe a circle.
;;         Please notice that radius has to be >= 0, since there is no circle with radius < 0.
;;         If you use radius=0 the struct describes a single point - think of using the point struct.
;;Example: (make-circle (make-point 1 1) 5)
;;         (make-circle (make-point 2 -1) 1)

;;Definition:
(define-struct circle(point radius)
)

;;Contract: circle_contains_circle: circle, circle  >>> bool
;;Purpose: Calculates if a circle c1 contains the second circle c2 with all points of c2.
;;         Uses func point_point_distance. Returns true if c2 is completly within c1; false if not.
;;Example: (circle_contains_circle (make-circle (make-point 1 1) 5) (make-circle (make-point 2 -1) 1))
;;         (circle_contains_circle (make-circle (make-point 2 2) 1) (make-circle (make-point -2 -1) 2))

;;Definition:
(define (circle_contains_circle c1 c2)
        (if (> (circle-radius c2) (circle-radius c1)) ;;cond
            false ;;if
            (if (> (+ (point_point_distance (circle-point c1) (circle-point c2)) (circle-radius c2)) (circle-radius c1)) ;; cond
                false ;;if
                true ;; else
            )
        )
)
;;Test
(check-expect (circle_contains_circle (make-circle (make-point 1 1) 5) (make-circle (make-point 2 -1) 1)) true)
(check-expect (circle_contains_circle (make-circle (make-point 2 2) 1) (make-circle (make-point -2 -1) 2)) false)

;;Contract: circle_contains_point: circle, point  >>> bool
;;Purpose: Calculates if a circle c1 contains the point p1. Uses the func point_point_distance.
;,         Returns true if point is within circle; false if not.
;;Example: (circle_contains_point (make-circle (make-point 1 1) 5) (make-point 1 1))
;;         (circle_contains_point (make-circle (make-point 2 2) 5) (make-point -5 1))

;;Definition:
(define (circle_contains_point c1 p1)
        (if (<= (point_point_distance (circle-point c1) p1) (circle-radius c1)) ;;cond
             true ;;if
             false ;;else
        )
)
;;Test
(check-expect (circle_contains_point (make-circle (make-point 1 1) 5) (make-point 1 1)) true)
(check-expect (circle_contains_point (make-circle (make-point 2 2) 5) (make-point -5 1)) false)

;;Contract: circle-contains-circle: number, number, number, number, number, number, number  >>> bool
;;Purpose: Does the same as circle_contains_circle, but constructs needed circle and points first.
;;         Therefore there are much more parameters - this func exists to match the exercise-requirements
;;         Param-sequence: c1x c1y c1r c2x c2y c2r
;;         For more info look at circle_contains_circle
;;Example: (circle-contains-circle 1 1 5  2 -1 1)
;;         (circle-contains-circle 2 2 1 -2 -1 2)

;;Definition:
(define (circle-contains-circle c1_x c1_y c1_r c2_x c2_y c2_r)
        (circle_contains_circle (make-circle (make-point c1_x c1_y) c1_r)
                                (make-circle (make-point c2_x c2_y) c2_r)
        )                        
)
;;Test
(check-expect (circle-contains-circle 1 1 5  2 -1 1) true)
(check-expect (circle-contains-circle 2 2 1 -2 -1 2) false)

;;Contract: circle-contains-point: number, number, number, number, number  >>> bool
;;Purpose: Does the same as circle_contains_point, but constructs needed circles and points first.
;;         Therefore there are much more parameters - this func exists to match the exercise-requirements
;;         Param-sequence: c1x c1y c1r p1x p1y.
;;         For more info look at circle_contains_point
;;Example: (circle-contains-point 1 1 5  1 1)
;;         (circle-contains-point 2 2 5 -5 1)

;;Definition:
(define (circle-contains-point c_x c_y c_r p_x p_y)
        (circle_contains_point (make-circle (make-point c_x c_y) c_r)
                               (make-point p_x p_y)
        )                         
)
;;Test
(check-expect (circle-contains-point 1 1 5  1 1) true)
(check-expect (circle-contains-point 2 2 5 -5 1) false)