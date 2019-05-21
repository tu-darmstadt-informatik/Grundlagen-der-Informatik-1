;; The first three lines of this file were inserted by DrScheme. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname u03) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f ())))
(define-struct tree (r a b))

(define (tree-insert t n)
        (if (and (tree? t)
                 (number? n)
             )
             (if (> n (tree-r t)) ;;
                 (if (tree? (tree-b t))
                     (make-tree (tree-r t) (tree-a t) (tree-insert (tree-b t) n))
                     (make-tree (tree-r t) (tree-a t) (make-tree n empty empty))
                 )
                 (if (tree? (tree-a t))
                     (make-tree (tree-r t) (tree-insert (tree-a t) n) (tree-b t))
                     (make-tree (tree-r t) (make-tree n empty empty) (tree-b t))
                 )
             )
             t;;else return tree
         )
)
  
(define some-tree
         (make-tree 4
                        (make-tree 2
                                   empty
                                  (make-tree 3
                                             empty
                                             empty))
                        (make-tree 6
                                  (make-tree 5
                                             empty
                                             empty)
                                  (make-tree 7
                                             empty
                                             empty)
                         )
              )

  )
  
;;(tree-insert some-tree 1)
;;(tree-insert (tree-insert some-tree 1) 0)
;;(tree-insert (tree-insert (tree-insert some-tree 9) 1) 0)
;;(tree-insert (tree-insert (tree-insert some-tree 7) 1) 0)

(define (tree-insert-list t l)
        (if (cons? l)
            (tree-insert-list (tree-insert t (first l)) (rest l)) 
            t
         )
)

(tree-insert-list some-tree (list 1 0 9 7))

(define sort-list 