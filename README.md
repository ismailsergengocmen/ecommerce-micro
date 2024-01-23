# Microservice Based Ecommerce Application

## Architecture

![Architecture Diagram](https://github.com/ismailsergengocmen/ecommerce-micro/assets/63583472/1d56559e-2417-442b-b0f7-8cc992d83eba)

This project is created to get hands-on experience on different microservice practices. It's implemented with Java, Spring Boot, MongoDB, PostgreSQL.

## Endpoints
  - Create product => POST localhost:8181/api/product
    ```JSON
    {
      "orderLineItemsDtoList": [
        {
            "skuCode":"phone",
            "price": 1200,
            "quantity": 1
        }
      ]
    }
    ```
  - Create order => POST localhost:8181/api/order
    ```JSON
    {
        "name": "phone",
        "description": "device",
        "price": 1200
    }
    ```
  - Get product list => GET localhost:8181/api/product

## Getting Started

### Prerequisites
- Docker

### Setting up the Project

1. **Clone the repository:**
    ```bash
    git clone git@github.com:ismailsergengocmen/ecommerce-micro.git
    ```

2. **Navigate to the project directory and start the database using Docker:**
    ```bash
    cd ecommerce-micro
    docker-compose up -d
    ```

3. **Access keycloak :**  
   Open a browser and navigate to [http://localhost:8080](http://localhost:8080)  
   Click on administration console and use username admin and password admin.  
   Then click on downward button next to "realm" and select "Spring-boot-microservices-realm".

4. **Get Client Secret:**  
   Select "Clients" from Configure section and click on "spring-cloud client".  
   Then select "Credentials" and click on "Regenerate Secret".  
   Copy the generated secret.

5. **Get access token Url:**  
   In the main menu, click on "Realm Settings" in Configure section.  
   Click on "OpenID Endpoint Configuration" and get the 3rd url (labeled "token_endpoint").  
   

6. **Access the application:**
   You can send requests using Postman. After configuring the "Authorization" section like given below,
   click on "Get New Access Token" and "Use Token" to send request.

   | Option                    | Value                                 |
   |---------------------------|---------------------------------------|
   | Type                      | Oauth 2.0                             |
   | Add authorization data to | Request Headers                       |
   | Use Token Type            | Access Token                          |
   | Header Prefix             | Bearer                                |
   | Grant Type                | Client credentials                    |
   | Access Token Url          | <The url you get from section 3.2>    |
   | Client ID                 | spring-cloud-client                   |
   | Client Secret             | <The secret you get from section 3.1> |
   | Scope                     | openid offline_access                 |
   | Client Authentication     | Send as Basic Auth header             |
