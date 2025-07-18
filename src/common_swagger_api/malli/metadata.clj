(ns common-swagger-api.malli.metadata)

(def Avu
  [:map
   [:id
    {:description         "The AVU's UUID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]

   [:attr
    {:description         "The Attribute's name"
     :json-schema/example "file_type"}
    :string]

   [:value
    {:description         "The Attribute's value"
     :json-schema/example "text"}
    :string]

   [:unit
    {:description         "The Attribute's unit"
     :json-schema/example ""}
    :string]

   [:target_id
    {:description         "The target item's UUID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440001"}
    :uuid]

   [:created_by
    {:description         "The ID of the user who created the AVU"
     :json-schema/example "user123"}
    :string]

   [:modified_by
    {:description         "The ID of the user who last modified the AVU"
     :json-schema/example "user123"}
    :string]

   [:created_on
    {:description         "The date the AVU was created in ms since the POSIX epoch"
     :json-schema/example 1634567890000}
    :int]

   [:modified_on
    {:description         "The date the AVU was last modified in ms since the POSIX epoch"
     :json-schema/example 1634567890000}
    :int]

   [:avus
    {:optional            true
     :description         "AVUs attached to this AVU"
     :json-schema/example []}
    [:vector [:ref ::Avu]]]])

(def AvuList
  [:map
   [:avus
    {:description         "The list of AVUs"
     :json-schema/example []}
    [:vector Avu]]])

(def AvuRequest
  [:map
   [:id
    {:optional            true
     :description         "The AVU's UUID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]

   [:attr
    {:description         "The Attribute's name"
     :json-schema/example "file_type"}
    :string]

   [:value
    {:description         "The Attribute's value"
     :json-schema/example "text"}
    :string]

   [:unit
    {:description         "The Attribute's unit"
     :json-schema/example ""}
    :string]

   [:target_id
    {:optional            true
     :description         "The target item's UUID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440001"}
    :uuid]

   [:created_by
    {:optional            true
     :description         "The ID of the user who created the AVU"
     :json-schema/example "user123"}
    :string]

   [:modified_by
    {:optional            true
     :description         "The ID of the user who last modified the AVU"
     :json-schema/example "user123"}
    :string]

   [:created_on
    {:optional            true
     :description         "The date the AVU was created in ms since the POSIX epoch"
     :json-schema/example 1634567890000}
    :int]

   [:modified_on
    {:optional            true
     :description         "The date the AVU was last modified in ms since the POSIX epoch"
     :json-schema/example 1634567890000}
    :int]

   [:avus
    {:optional            true
     :description         "AVUs attached to this AVU"
     :json-schema/example []}
    [:vector [:ref ::AvuRequest]]]])

(def AvuListRequest
  [:map
   [:avus
    {:description         "The AVUs to save for the target item"
     :json-schema/example []}
    [:vector AvuRequest]]])

(def SetAvuRequest
  [:map
   [:avus
    {:optional            true
     :description         "The AVUs to save for the target item"
     :json-schema/example []}
    [:vector AvuRequest]]])

(def AvuSearchParams
  [:map
   [:attribute
    {:optional            true
     :description         "Attribute names to search for"
     :json-schema/example ["file_type"]}
    [:vector :string]]

   [:value
    {:optional            true
     :description         "Values to search for"
     :json-schema/example ["text"]}
    [:vector :string]]

   [:unit
    {:optional            true
     :description         "Units to search for"
     :json-schema/example [""]}
    [:vector :string]]

   [:target-id
    {:optional            true
     :description         "Target IDs to search for"
     :json-schema/example ["550e8400-e29b-41d4-a716-446655440000"]}
    [:vector :uuid]]])

(def DataTypeEnum
  [:enum "file" "folder"])