var userAuthRequirement = {
  "name" : "Basic Authentication (User)",
  "description" : "Basic Authentication with the username and password associated with the user",
  "example" : "HTTP Header - 'Authentication: Basic ' + base64(username + ':' + password)"
};

var sessionAuthRequirement = {
  "name" : "Basic Authentication (Session)",
  "description" : "Basic Authentication with the username associated with the user, and the session key associated with the employee",
  "example" : "HTTP Header - 'Authentication: Basic ' + base64(username + ':' + sessionKey)"
};

var employeeOwnerOrManager = {
  "name" : "Employee Ownership or Management",
  "description" : "The access this method, you must be the employee in question, or you must be a manager to the employee."
};

var employeeManager = {
  "name" : "Employee Management",
  "description" : "The access this method, you must be a manager to the employee."
};

var groupManager = {
  "name" : "Group",
  "description" : "The access this method, you must be a manager to the group."
};

var badAuthResult = {
  "result" : "The Basic Authentication credentials were invalid"
};

var invalidID400 = {
  "result" : "The unique identifier passed was invalid."
};

var apiDef = {
  "paths" : {
    // ---------------------------------------------------------------------------------------------------
    "/accounts/user/full" : {
      "GET" : {
        "shortDescription" : "Gets all Account data for a user",
        "requires" : [ userAuthRequirement ],
        "responses" : {
          401 : badAuthResult,
          200 : {
            "result" : "The full user object",
            "data" : {
              "user" : {
                "id" : 1,
                "username" : "username",
                "email" : "email@address.com"
              },
              "businesses" : [
                {
                  "id" : 1,
                  "name" : "Business Name",
                  "server" : "some.domain",
                  "employeeID" : 1,
                  "image" : {
                    "id" : 1,
                    "alt" : "Alternate text"
                  },
                  "address" : {
                    "street1" : "101 Loop Lane",
                    "street2" : "Suite 8",
                    "city" : "Somewhere",
                    "subDivision" : "In Cali",
                    "country" : "USA",
                    "postalCode" : "12345",
                    "phoneNumber" : "18885551234"
                  }
                },
                {
                  "id" : 2,
                  "name" : "Business Name 2",
                  "server" : "some.domain2",
                  "employeeID" : 3,
                  "image" : {
                    "id" : 2,
                    "alt" : "Alternate text"
                  },
                  "address" : {
                    "street1" : "101 Loop Lane",
                    "street2" : "Suite 8",
                    "city" : "Somewhere",
                    "subDivision" : "In Cali",
                    "country" : "USA",
                    "postalCode" : "12345",
                    "phoneNumber" : "18885551234"
                  }
                }
              ]
            }
          }
        }
      }
    },
    // ---------------------------------------------------------------------------------------------------
    "/accounts/user/self" : {
      "GET" : {
        "shortDescription" : "Gets basic user info",
        "requires" : [ userAuthRequirement ],
        "responses" : {
          401 : badAuthResult,
          200 : {
            "result" : "The basic user object",
            "data" : {
              "id" : 1,
              "username" : "username",
              "email" : "email@address.com"
            }
          }
        }
      }
    },
    // ---------------------------------------------------------------------------------------------------
    "/accounts/user/session" : {
      "PUT" : {
        "shortDescription" : "Creates a new session token",
        "longDescription" : "The method will create a sessions key for use as a temporary password on a business server for a given employee business. The key is unique to the issuing user and will timeout after the allotted timeout period if it is not used.",
        "requires" : [ userAuthRequirement ],
        "takes" : {
          "businessID" : 1,
          "employeeID" : 1
        },
        "responses" : {
          401 : badAuthResult,
          201 : {
            "result" : "A session was created",
            "data" : {
              "sessionKey" : "Some session id",
              "server" : "someserver.domain",
              "timeout" : 1
            }
          }
        }
      },
      "DELETE" : {
        "shortDescription" : "Destroys and existing session token",
        "requires" : [ userAuthRequirement ],
        "takes" : {
          "businessID" : 1,
          "employeeID" : 1,
          "sessionKey" : "The session id to destroy"
        },
        "responses" : {
          401 : badAuthResult,
          202 : {
            "result" : "A session was destroyed"
          }
        }
      }
    },
    // ---------------------------------------------------------------------------------------------------
    "/business/group/{ids}" : {
      "GET" : {
        "shortDescription" : "Gets simple group data",
        "requires" : [ sessionAuthRequirement ],
        "params" : {
          "ids" : "A dash seperated list of valid group IDs. (Example: ../group/1-5-6-13)"
        },
        "responses" : {
          400 : invalidID400,
          401 : badAuthResult,
          200 : {
            "result" : "A mapping of simple group data objects. (Group ID is the key).",
            "data" : {
              1 : {
                "groupID" : 1,
                "name" : "Some Name",
                "parentGroupID" : 2,
                "roleIDs" : [ 1, 2, 3 ]
              }
            }
          }
        }
      }
    },
    // ---------------------------------------------------------------------------------------------------
    "/business/group" : {
      "PUT" : {
        "shortDescription" : "Adds a group",
        "requires" : [ sessionAuthRequirement, groupManager ],
        "takes" : {
          "name" : "Some Name",
          "parentGroupID" : 1,
          "roleIDs" : [ 1, 2, 3 ]
        },
        "responses" : {
          400 : invalidID400,
          401 : badAuthResult,
          200 : {
            "result" : "The group was added"
          }
        }
      }
    },
    // ---------------------------------------------------------------------------------------------------
    "/business/group/employee" : {
      "PUT" : {
        "shortDescription" : "Adds an employee to a group",
        "requires" : [ sessionAuthRequirement, employeeManager, groupManager ],
        "takes" : {
          "groupID" : 1,
          "employeeIDs" : [1, 2, 3]
        },
        "responses" : {
          400 : invalidID400,
          401 : badAuthResult,
          200 : {
            "result" : "The employees were added to the group"
          }
        }
      }
    },
    // ---------------------------------------------------------------------------------------------------
    "/business/group/role" : {
      "PUT" : {
        "shortDescription" : "Adds an role to a group",
        "requires" : [ sessionAuthRequirement, groupManager ],
        "takes" : {
          "groupID" : 1,
          "roleNames" : ["New", "Role Names", "To Add"]
        },
        "responses" : {
          400 : invalidID400,
          401 : badAuthResult,
          200 : {
            "result" : "The roles were added",
            "data" : {
              1 : {
                "groupID" : 1,
                "name" : "Some Name",
                "parentGroupID" : 2,
                "roleIDs" : [ 1, 2, 3 ]
              }
            }
          }
        }
      }
    },
    // ---------------------------------------------------------------------------------------------------
    "/business/group/role/employee" : {
      "PUT" : {
        "shortDescription" : "Adds an employee to a group role",
        "requires" : [ sessionAuthRequirement, employeeManager, groupManager ],
        "takes" : {
          "groupID" : 1,
          "roleID" : 2,
          "employeeIDs" : [1, 2, 3]
        },
        "responses" : {
          400 : invalidID400,
          401 : badAuthResult,
          200 : {
            "result" : "The employees were added to the group role"
          }
        }
      }
    },
    // ---------------------------------------------------------------------------------------------------
    "/business/role/{ids}" : {
      "GET" : {
        "shortDescription" : "Gets simple role data",
        "requires" : [ sessionAuthRequirement ],
        "params" : {
          "ids" : "A dash seperated list of valid role IDs. (Example: ../role/1-5-6-13)"
        },
        "responses" : {
          400 : invalidID400,
          401 : badAuthResult,
          200 : {
            "result" : "A mapping of simple role data objects. (Role ID is the key).",
            "data" : {
              1 : {
                "roleID" : 1,
                "name" : "Some Name"
              }
            }
          }
        }
      }
    },
    // ---------------------------------------------------------------------------------------------------
    "/business/employee/{id}" : {
      "GET" : {
        "shortDescription" : "Gets basic information about an employee",
        "requires" : [ sessionAuthRequirement ],
        "params" : {
          "id" : "A valid employee ID."
        },
        "responses" : {
          400 : invalidID400,
          401 : badAuthResult,
          200 : {
            "result" : "The base employee object",
            "data" : {
              "employeeID":1,
              "firstName" : "Joe",
              "lastName" : "Shmoe",
              "defaultGroupID" : 1
            }
          }
        }
      }
    },
    // ---------------------------------------------------------------------------------------------------
    "/business/employee" : {
      "PUT" : {
        "shortDescription" : "Adds a new employee",
        "requires" : [ sessionAuthRequirement, groupManager ],
        "takes" : {
          "firstName" : "First",
          "lastName" : "Last",
          "homeGroupID" : 1,
          "groupRoleIDs": {
            "1" : [1, 2, 3],
            "2" : [4, 5, 6]
          }
        },
        "responses" : {
          400 : invalidID400,
          401 : badAuthResult,
          200 : {
            "result" : "The employee was added",
            "data" : {
              "employee" : {
                "employeeID":1,
                "firstName" : "Joe",
                "lastName" : "Shmoe",
                "defaultGroupID" : 1
              },
              "groupRoleIDs" : {
                1 : [ 1, 2 ],
                2 : [ 2, 3 ]
              }
            }
          }
        }
      }
    },
    // ---------------------------------------------------------------------------------------------------
    "/business/employee/full/{id}" : {
      "GET" : {
        "shortDescription" : "Gets all information about an employee",
        "requires" : [ sessionAuthRequirement, employeeOwnerOrManager ],
        "params" : {
          "id" : "A valid employee ID."
        },
        "responses" : {
          401 : badAuthResult,
          200 : {
            "result" : "The full employee object",
            "explanation" : "The 'groupRoles' object contains a map of <GroupID -> Array<RoleID>>.",
            "data" : {
              "employee" : {
                "employeeID":1,
                "firstName" : "Joe",
                "lastName" : "Shmoe",
                "defaultGroupID" : 1
              },
              "groupRoleIDs" : {
                1 : [ 1, 2 ],
                2 : [ 2, 3 ]
              }
            }
          }
        }
      }
    }
    // ---------------------------------------------------------------------------------------------------
  }
};