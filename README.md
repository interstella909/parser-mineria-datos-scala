# Invoice Processing with Scala and MySQL

Welcome to the Invoice Processing repository! This project demonstrates how to read invoice data from a text file and insert it into a MySQL database using Scala. The main objective is to illustrate efficient data processing and database interaction techniques, including the use of JDBC and prepared statements.
Project Overview

The application processes invoice data contained in a text file (facturas.txt). The file includes three types of records: headers (H), items (I), and trailers (T). Each record type is parsed and inserted into the respective tables (Factura, Item, and Trailer) in the MySQL database.
Key Features

    Scala Integration with MySQL: Utilize Scala's JDBC capabilities to connect and interact with a MySQL database.
    Data Parsing and Validation: Read and parse lines from the input file, and validate the data before insertion.
    Efficient Data Insertion: Use prepared statements to securely and efficiently insert data into the database.
    Error Handling: Implement exception handling to manage SQL and I/O errors gracefully.
