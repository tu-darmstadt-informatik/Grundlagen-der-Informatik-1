;; The first three lines of this file were inserted by DrScheme. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname point) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ())))
;;Contract: point: number, number  >>> point
;;Purpose: X
;;Example:

;;Definition:
(define-struct point(x y))
;;Test
(make-point 1 1)