;; The first three lines of this file were inserted by DrScheme. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-abbr-reader.ss" "lang")((modname students) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ())))
;;Contract: make-student: number, symbol  >>> student
;;Purpose: Represents data of a student;
;;         id = student-id, fos = fieldofstudy
;;Example: (make-student 1 'inf)
;;         (make-student 2 'winf)

;;Definition:
(define-struct student(id fos))

;;Purpose: A list with some sample-students
(define some-students
   (list (make-student 13 'inf)
         (make-student 532 'winf)
         (make-student 352 'ce)
         (make-student 54 'inf)
         (make-student 256 'ist)
   )
)

;;Purpose: A list with some sample-fos
(define some-dist
   (list 'inf 'winf 'ce 'ist)
)

;;Purpose: A second list with some sample-fos
(define some-more-dist
   (list 'winf 'ce)
)

;;Conmtract: count-students-for: (listof student) symbol  >>> number
;;Purpose: Returns the number of students (provided by the given list of students)
;;         who are in the fos given by fos-param.
;;         Is recursiv
;;Example: (count-students-for some-students 'inf)
;;         (count-students-for some-students 'winf)

;;Definition:
(define (count-students-for slist fos)
        (if (and (cons? slist)
                 (student? (first slist))
                 (symbol? fos)
            ) ;;cond
            (if (symbol=? (student-fos (first slist)) fos) ;;cond
                (+  1
                   (count-students-for (rest slist) fos)
                ) ;;if
                (count-students-for (rest slist) fos) ;; else
            );;if
            0;;else
        )
)

;;Test
(check-expect (count-students-for some-students 'inf) 2)
(check-expect (count-students-for some-students 'winf) 1)

;;Contract: count-student-dist: (listof student), (listof symbol) >>> (listof number)
;;Purpose: Returns a list of numbers, representing the numbersof students in fos.
;;         Order of the numbers is given by the order of the fos in foslist-parameter.
;;         Uses count-students-for; Is recursiv
;;Example: (count-student-dist some-students some-dist)
;;         (count-student-dist some-students some-more-dist)

;;Definition:
(define (count-student-dist slist foslist)
        (if (cons? foslist) ;;cond ;;Do not check slist - it is checked in count-students-for
            (append (list (count-students-for slist (first foslist))) ;;Append listelemnet
                    (count-student-dist slist (rest foslist))
            ) ;;if
            (list) ;;else (empty list)
        )
)

;;Test
(check-expect (count-student-dist some-students some-dist) (list 2 1 1 1))
(check-expect (count-student-dist some-students some-more-dist) (list 1 1))

;;Contract: print-student-list: (listof student), (listof symbol) >>> string
;;Purpose: Constructs a string of the following form:
;;         fos: number fos2: number
;;         Uses count-students-for; Is recursiv
;;Notice:  Could have used count-student-dist instead of count-students-for
;;         But I think this is much more simple.
;;Example: (print-student-list some-students some-dist)
;;         (print-student-list some-students some-more-dist)

;;Definition:
(define (print-student-list slist symlist)
        (if (cons? symlist);;cond ;;Do not check slist - it is checked in count-students-for
            (string-append (symbol->string (first symlist))
                            ": "
                           (number->string (count-students-for slist (first symlist)))
                            " "
                           (print-student-list slist (rest symlist))
            );;if ("fos: number ")
            "" ;;else (empty string)
        )
)

;;Test
(check-expect (print-student-list some-students some-dist) "inf: 2 winf: 1 ce: 1 ist: 1 ")
(check-expect (print-student-list some-students some-more-dist) "winf: 1 ce: 1 ")