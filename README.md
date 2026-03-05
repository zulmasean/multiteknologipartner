# API Automation Testing -- RestAssured

Project ini merupakan **automation testing framework untuk REST API**
menggunakan:

-   Java
-   RestAssured
-   JUnit 5
-   Maven

Framework ini mengimplementasikan **API Client Pattern** serta
memisahkan komponen seperti authentication, API client, database
validation, dan test cases agar kode lebih **clean, reusable, dan
scalable**.

------------------------------------------------------------------------

# Tech Stack

  Technology    Description
  ------------- ---------------------------
  Java          Programming language
  RestAssured   Library untuk API testing
  JUnit 5       Testing framework
  Maven         Dependency management
  MySQL         Database validation
  JDBC          Database connection

------------------------------------------------------------------------

# Project Structure

    multiteknologipartner
    │
    ├── src
    │   └── test
    │       └── java
    │
    │           ├── base
    │           │   ├── AuthHelper.java
    │           │   └── BaseTest.java
    │           │
    │           ├── client
    │           │   └── OrdersClient.java
    │           │
    │           ├── db
    │           │   └── DBHelper.java
    │           │
    │           ├── model
    │           │   └── OrderRequest.java
    │           │
    │           └── tests
    │               └── OrdersTest.java
    │
    ├── pom.xml
    └── README.md

------------------------------------------------------------------------

# Architecture Overview

Framework ini menggunakan pola berikut:

    Test
     ↓
    Client (API Layer)
     ↓
    RestAssured
     ↓
    API Server
     ↓
    Database Validation

Komponen utama:

-   Base Layer → setup environment & authentication
-   Client Layer → semua request API
-   Model Layer → request body object
-   DB Layer → validasi database
-   Test Layer → test scenario

------------------------------------------------------------------------

# Base Package

## AuthHelper

Class ini digunakan untuk melakukan **authentication ke API** dan
mendapatkan **JWT token**.

Token ini digunakan untuk request API berikutnya.

Request:

    POST /api/auth/login

Body:

``` json
{
  "username": "zulmairzamsyah",
  "password": "test123"
}
```

Return:

    JWT Token

------------------------------------------------------------------------

## BaseTest

Class ini menjadi **base class untuk semua test**.

Fungsi:

-   Setup baseURI
-   Mengambil authentication token
-   Token akan digunakan oleh seluruh test

Contoh:

``` java
@BeforeAll
static void setup() {
    RestAssured.baseURI = "https://api.multiteknologipartner.com";
    token = AuthHelper.getToken();
}
```

------------------------------------------------------------------------

# Client Package

## OrdersClient

Class ini berisi **semua request ke Orders API**.

  Method   Endpoint           Description
  -------- ------------------ --------------
  POST     /api/orders        Create order
  GET      /api/orders/{id}   Get order
  PUT      /api/orders/{id}   Update order
  DELETE   /api/orders/{id}   Delete order

Semua request menggunakan header:

    Authorization: Bearer <token>

Contoh penggunaan:

``` java
OrdersClient.createOrder(token, order);
```

------------------------------------------------------------------------

# Model Package

## OrderRequest

Class ini merupakan **POJO model** untuk request body order.

``` java
public class OrderRequest {

    public String id;
    public String customer_name;
    public String product_code;
    public int quantity;
    public double price;
    public double total;
    public String status;

}
```

RestAssured otomatis mengubah object menjadi JSON.

------------------------------------------------------------------------

# Database Validation

## DBHelper

Class ini digunakan untuk **memverifikasi data di database** setelah API
dipanggil.

Connection menggunakan JDBC ke MySQL.

Method:

``` java
public static boolean orderExists(String id)
```

Query:

    SELECT * FROM orders WHERE id='<order_id>'

Return:

    true  → data ada di database
    false → data tidak ada

------------------------------------------------------------------------

# Test Package

## OrdersTest

Class ini berisi **automation test scenarios untuk Orders API**.

Framework menggunakan:

-   JUnit 5
-   RestAssured
-   Hamcrest Assertion

------------------------------------------------------------------------

# Test Scenarios

## 1 Create Order -- Positive Case

Expected:

-   201 CREATED
-   status = CREATED
-   data tersimpan di database

Database validation:

``` java
assert DBHelper.orderExists(id);
```

------------------------------------------------------------------------

## 2 Create Order -- Quantity Zero

Negative validation.

    quantity = 0
    Expected: 400 Bad Request

------------------------------------------------------------------------

## 3 Create Order -- Boundary Test

Boundary testing.

    quantity = 1
    Expected: 201 Created

------------------------------------------------------------------------

## 4 Create Order -- Invalid Total

Business rule:

    total ≠ quantity * price
    Expected: 400 Bad Request

------------------------------------------------------------------------

## 5 Create Order -- Duplicate ID

    create order dua kali dengan id sama
    Expected: 409 Conflict

------------------------------------------------------------------------

## 6 Update Order -- Paid Order

Business rule:

    Jika order sudah PAID
    total tidak boleh berubah
    Expected: 400 Bad Request

------------------------------------------------------------------------

## 7 Delete Order -- Idempotent

    delete pertama → 204
    delete kedua → tetap 204

------------------------------------------------------------------------

# How to Run Test

## Clone Repository

    git clone https://github.com/username/multiteknologipartner.git

## Masuk ke Folder

    cd multiteknologipartner

## Run Test

    mvn clean test

------------------------------------------------------------------------

# Example Test Execution Flow

    BaseTest
       ↓
    Get Authentication Token
       ↓
    Create Order API
       ↓
    Validate Response
       ↓
    Validate Database

------------------------------------------------------------------------

# Best Practices Used

Framework ini menerapkan:

-   API Client Pattern
-   Separation of Concern
-   Reusable Authentication
-   Database Validation
-   Boundary Testing
-   Negative Testing
-   Idempotency Testing

------------------------------------------------------------------------

# Future Improvements

-   Allure Report
-   Environment configuration (dev/staging/prod)
-   Request & Response logging
-   CI/CD integration
-   Parallel execution

------------------------------------------------------------------------

# Author

Zulma Irzamsyah - QA Engineer

zulma.sean84@gmail.com