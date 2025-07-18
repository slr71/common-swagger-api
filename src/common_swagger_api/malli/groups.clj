(ns common-swagger-api.malli.groups)

(def ValidGroupPrivileges
  [:enum "view" "read" "update" "admin" "optin" "optout" "groupAttrRead" "groupAttrUpdate"])

(def BaseGroup
  [:map
   [:name
    {:description         "The internal group name"
     :json-schema/example "group:example"}
    :string]
   [:type
    {:description         "The group type name"
     :json-schema/example "group"}
    :string]
   [:description
    {:optional            true
     :description         "A brief description of the group"
     :json-schema/example "Example group for demonstration"}
    :string]
   [:display_extension
    {:optional            true
     :description         "The displayable group name extension"
     :json-schema/example "example"}
    :string]])

(def Group
  [:map
   BaseGroup
   [:display_name
    {:optional            true
     :description         "The displayable group name"
     :json-schema/example "Example Group"}
    :string]
   [:id_index
    {:optional            true
     :description         "The group's ID index"
     :json-schema/example 12345}
    :int]
   [:extension
    {:optional            true
     :description         "The group's extension"
     :json-schema/example "example"}
    :string]])

(def GroupDetailsParam
  [:map
   [:details
    {:optional            true
     :description         "Optionally include group details such as modified date and creator information"
     :json-schema/example true}
    :boolean]])

(def GroupMember
  [:map
   [:id
    {:description         "The member's ID"
     :json-schema/example "user123"}
    :string]
   [:name
    {:description         "The member's name"
     :json-schema/example "user123"}
    :string]
   [:first_name
    {:optional            true
     :description         "The member's first name"
     :json-schema/example "John"}
    :string]
   [:last_name
    {:optional            true
     :description         "The member's last name"
     :json-schema/example "Doe"}
    :string]
   [:email
    {:optional            true
     :description         "The member's email address"
     :json-schema/example "john.doe@example.com"}
    :string]
   [:institution
    {:optional            true
     :description         "The member's institution"
     :json-schema/example "Example University"}
    :string]
   [:source_id
    {:optional            true
     :description         "The member's source ID"
     :json-schema/example "ldap"}
    :string]])

(def GroupWithMembers
  [:map
   Group
   [:members
    {:description         "The group members"
     :json-schema/example []}
    [:vector GroupMember]]])

(def GroupPrivilege
  [:map
   [:name
    {:description         "The privilege name"
     :json-schema/example "read"}
    ValidGroupPrivileges]
   [:allowed
    {:description         "Whether the privilege is allowed"
     :json-schema/example true}
    :boolean]])

(def GroupWithPrivileges
  [:map
   Group
   [:privileges
    {:description         "The group privileges"
     :json-schema/example []}
    [:vector GroupPrivilege]]])

(def GroupUpdateRequest
  [:map
   [:description
    {:optional            true
     :description         "A brief description of the group"
     :json-schema/example "Updated group description"}
    :string]
   [:display_extension
    {:optional            true
     :description         "The displayable group name extension"
     :json-schema/example "updated-example"}
    :string]])

(def GroupMembershipRequest
  [:map
   [:members
    {:description         "The list of member IDs to add or remove"
     :json-schema/example ["user123" "user456"]}
    [:vector :string]]])

(def GroupPrivilegeRequest
  [:map
   [:privileges
    {:description         "The list of privileges to grant or revoke"
     :json-schema/example []}
    [:vector GroupPrivilege]]])