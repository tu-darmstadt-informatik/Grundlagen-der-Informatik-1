;; The first three lines of this file were inserted by DrScheme. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname h04) (read-case-sensitive #t) (teachpacks ((lib "image.ss" "teachpack" "htdp"))) (htdp-settings #(#t constructor repeating-decimal #t #t none #f ((lib "image.ss" "teachpack" "htdp")))))
;;Highway-Image
;;(define highway PICHERE)

;;Purpose: Horizontal Sobel-Operator
(define sobel-we (list 2 0 -2))

;; Contract: brightness: color -> number
;; Purpose:  Calculates the brightness of a color-struct.
;; Example:  (brightness (make-color 0 0 0))
;;           (brightness (make-color 255 0 255))
(define (brightness color)
	(/ (+ (color-red color)
	      (color-green color)
              (color-blue color)
	   ) 
	   3
	)
)

;; Test
(check-expect (brightness (make-color 0 0 0)) 0)
(check-expect (brightness (make-color 255 0 255)) 170)

;; Contract: append2loc: (listof color) -> (listof color)
;; Purpose:  Appends two color-struct(0 0 0) at a given list loc
;;           Notice: Its not checked if parameter is listof color!
;;                   It checks if parameter loc is a list!
;; Example:  (append2loc (list (make-color 0 0 0)))
;;           (append2loc (list (make-color 0 0 0) (make-color 0 0 0)))
(define (append2loc loc)
	(if (cons? loc)
            (append loc (list (make-color 0 0 0) (make-color 0 0 0)))
            (error 'append2loc "Not a list")
	)
)
;; Test
(check-expect (append2loc (list (make-color 0 0 0))) ;;command
              (list (make-color 0 0 0) (make-color 0 0 0) (make-color 0 0 0))) ;;expect
(check-expect (append2loc (list (make-color 0 0 0) ;;command
              (make-color 0 0 0))) (list (make-color 0 0 0) (make-color 0 0 0) (make-color 0 0 0) (make-color 0 0 0))) ;;expect

;; Contract: apply-for-env3x1: (listof color) ((listof color) -> color) -> (listof color)
;; Purpose:  calculates loc2c on every tripel in given color-list loc.
;;           returns a listof color.
;;           uses append2loc to calculate last element.
;;           is recursiv!
;; Example:  (apply-for-env3x1 (list (make-color 1 2 3) (make-color 4 5 6) (make-color 7 8 9)) (lambda(loc) (first loc)))
;;           (apply-for-env3x1 (list (make-color 255 255 255) (make-color 4 5 6) (make-color 10 10 10)) (lambda(loc) (first loc)))
(define (apply-for-env3x1 loc loc2c)
	(if (>= (length  loc) 3) ;;check if list is long enaugh
            (append (list (loc2c (list (first (append2loc loc)) ;;calc first element
                                       (second (append2loc loc))
                                       (third (append2loc loc))
                                 )
                          )
                    )
                    (apply-for-env3x1 (rest loc) loc2c) ;;calc rest elements recursivly
            )
            loc ;;ancor
        )
)
;; Test
(check-expect (apply-for-env3x1 (list (make-color 1 2 3)
                                      (make-color 4 5 6)
                                      (make-color 7 8 9)
                                )
                                (lambda(loc) (first loc))
              );;command
              (list (make-color 1 2 3) ;;expect
                    (make-color 4 5 6)
                    (make-color 7 8 9)
              )
)
(check-expect (apply-for-env3x1 (list (make-color 255 255 255)
                                      (make-color 4 5 6)
                                      (make-color 10 10 10)
                                )
                                (lambda(loc) (first loc))
              );;command
              (list (make-color 255 255 255) ;;expect
                    (make-color 4 5 6)
                    (make-color 10 10 10)
              )
)

;; Contract: numberfitcolor: number -> number
;; Purpose:  Checks if number is a valid value
;;           for a color struct. If not produces
;;           a valid value.
;;           Uses min,floor and abs.
;; Example: (numberfitcolor -5)
;;          (numberfitcolor 267.3)
(define (numberfitcolor n)
	(min 255 ;;define maximum@255
             (floor (abs n))) ;; round down
)
;; Test
(check-expect (numberfitcolor -5) ;;command
              5 ;;expect
)
(check-expect (numberfitcolor 267.3) ;;command
              255 ;;expect
)

;; Contract: vec-multi: (listof number) (listof number) -> number
;; Purpose:  Calculates Scalar-product of two vectors
;; Example:  (vec-multi '(1 2 3) '(1 2 3))
;;           (vec-multi '(1 2 3 4) '(1 2 3 4))
(define (vec-multi v1 v2)
        (if (and (cons? v1) ;;is a list?
                 (cons? v2) ;;is a list?
            )
            (foldl + ;;add all elements in the generated list
                   0
                   (map * ;;multip. every element of a vector
                        v1
                        v2
                   )
            );;if
            0 ;;else
        )
)

;; Test
(check-expect (vec-multi '(1 2 3)
                         '(1 2 3)
              );;command
              14;;expect
)
(check-expect (vec-multi '(1 2 3 4)
                         '(1 2 3 4)
              );;comamnd
              30;;expect
)

;; Contract: scalarofcolorsobel-we: color -> number
;; Purpose:  Calculates scalar-product of a color-struct
;;           and sobel-we
;;           Makes sure result is a valid color-value.
;;           Uses vec-multi, sobel-we
;; Example:  
(define (scalarofcolorsobel-we color)
        (if (color? color)
            (numberfitcolor (vec-multi (list (color-red color) ;;numberfitcolor -> ensures generated number is a valid rbg-value
                                             (color-green color)
                                             (color-blue color)) ;;list representing a color
                                       sobel-we ;;sobel-parameter
                            )
            )
            (error 'scalarofcolorsobel-we "Not a color") ;; parameter was not a color
        )
)
;; Test
(check-expect (scalarofcolorsobel-we (make-color 0 0 0));;command
              0;;expect
)
(check-expect (scalarofcolorsobel-we (make-color 255 255 255));;command
              0;;expect
)

;; Contract: scalarofcolorsoel-we-brightness: color color color -> number
;; Purpose:  calculates the scalar of the brightness of a color-tripel
;;           with the sobel-we vector
;;           uses scalarofcolorsobel-we
;; Example:  (scalarofcolorsobel-we-brightness (make-color 0 0 0) (make-color 0 0 0) (make-color 0 0 0))
;;           (scalarofcolorsobel-we-brightness (make-color 255 255 255) (make-color 1 1 1) (make-color 1 1 1))
(define (scalarofcolorsobel-we-brightness c1 c2 c3)
        (scalarofcolorsobel-we (make-color (brightness c1) ;;create color-struct here 2 fit scalarofcolorsobel-we
                                           (brightness c2)
                                           (brightness c3))
        )
)
;; Test
(check-expect (scalarofcolorsobel-we-brightness (make-color 0 0 0)
                                                (make-color 0 0 0)
                                                (make-color 0 0 0)
              );;command
              0;;expect
)
(check-expect (scalarofcolorsobel-we-brightness (make-color 255 255 255)
                                                (make-color 1 1 1)
                                                (make-color 1 1 1)
              );;command
              255;;expect
)

;; Contract: sobel: (list color color color) -> color
;; Purpose:
;; Example:
(define (sobel lo3c)
	(if (and (cons? lo3c) ;;parameter is list
		 (color? (first lo3c)) ;; list contains colors?
		 (color? (second lo3c))
		 (color? (third lo3c))
	    )
	    (make-color (scalarofcolorsobel-we-brightness (first lo3c)  ;;calculate brightness value
                                                          (second lo3c)
                                                          (third lo3c))
                        (scalarofcolorsobel-we-brightness (first lo3c)  ;;twice^^
                                                          (second lo3c)
                                                          (third lo3c))
                        (scalarofcolorsobel-we-brightness (first lo3c) ;;third time^^ -> i hate scheme
                                                          (second lo3c)
                                                          (third lo3c))
            )
            (error 'sobel "Not a listof color") ;;Parametermissmatch
        )
)
;; Test
(check-expect (sobel (list (make-color 0 0 60)
			   (make-color 0 0 0)
			   (make-color 0 60 0)));;command
	      (make-color 0 0 0));;expect
(check-expect (sobel (list (make-color 0 0 20) 
			   (make-color 127 0 255) 
			   (make-color 0 30 40)));;command
	      (make-color 33 33 33));;expect

;; Contract: image-transform-3x1: image ((listof color) -> color) -> image
;; Purpose:  Transforms image into gray-value-image
;; Example:  Not needed
(define (image-transform-3x1 img loc2c)
        (color-list->image (apply-for-env3x1 (image->color-list img) ;;convert image 2 color-list, filter via apply-for-env3x1 and loc2c, convert back 2 image
                                              loc2c)
                           (image-width img) ;;img width
                           (image-height img) ;;img height
                           0 ;;dont care 4 that
                           0 ;;dont care 4 that
        )
)
;; Test
(image-transform-3x1 highway sobel) ;;test here ;-)