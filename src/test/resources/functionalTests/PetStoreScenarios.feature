Feature: A user should be able to validate all the scenarios related to petstore.
     

  Scenario: The user should be able to validate available pets
     Given I can request for available pets in the store
     
  Scenario: The user should be able to add pets to the store
     When I add the pet to the store
     Then I expect the pet to be added to store

  Scenario: The user should be able to change status of pets in store
     When I change status of pet to sold       
     Then I expect the status of pet to be set to sold

  Scenario: The user should be able to remove pets from store
     When I remove the pet from store       
     Then I expect the pet to be removed from store