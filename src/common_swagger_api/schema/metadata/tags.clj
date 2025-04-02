(ns common-swagger-api.schema.metadata.tags
  (:require [clojure-commons.error-codes :as ce]
            [common-swagger-api.schema :refer [->optional-param
                                               describe
                                               ErrorResponse
                                               ErrorResponseIllegalArgument
                                               ErrorResponseNotFound
                                               ErrorResponseUnchecked
                                               NonBlankString]]
            [common-swagger-api.schema.metadata :refer [DataTypeEnum]]
            [schema.core :as s])
  (:import [java.util UUID]))

(def TagIdPathParam (describe UUID "The tag's UUID"))
(def TagValueString (s/both NonBlankString (s/pred #(<= (count %) 255) 'valid-tag-value-size?)))
(def TagSuggestLimit (s/both Long (s/pred (partial < 0) 'valid-tag-suggest-limit?)))

(def GetTagsSummary "List All Attached Tags for a User")
(def GetTagsDescription "This endpoint lists all tags that have been attached to a file or folder by a user.")
(def DeleteTagsSummary "Permanently Delete All Attached Tags for a User")
(def DeleteTagsDescription "This endpoint permanently deletes all tag attachments that have been added to a file or folder by a user.")
(def GetAttachedTagSummary "List Attached Tags")
(def GetAttachedTagDescription "This endpoint lists the tags of the user that are attached to the indicated file or folder.")
(def PatchTagsSummary "Attach/Detach Tags to a File/Folder")
(def PatchTagsDescription "
Depending on the `type` parameter, this endpoint either attaches a set of the authenticated user's
tags to the indicated file or folder, or it detaches the set.")
(def GetTagSuggestionsSummary "Suggest a Tag")
(def GetTagSuggestionsDescription "
Given a textual fragment of a tag's value, this endpoint will list up to a given number of the
authenticated user's tags that contain the fragment.")
(def GetUserTagsSummary "List Tags Defined by a User")
(def GetUserTagsDescription "This endpoint lists all of the tags defined by a user.")
(def DeleteUserTagsSummary "Delete Tags Defined by a User")
(def DeleteUserTagsDescription "This endpoint deletes all tags defined by the current user. Corresponding attached tags will also be deleted.")
(def PostTagSummary "Create a Tag")
(def PostTagDescription "This endpoint creates a tag for use by the authenticated user.")
(def DeleteTagSummary "Delete a Tag")
(def DeleteTagDescription "This endpoint allows a user tag to be deleted, detaching it from all metadata.")
(def PatchTagSummary "Update Tag Labels/Descriptions")
(def PatchTagDescription "This endpoint allows a tag's label and description to be modified by the owning user.")

(s/defschema TagTypeEnum {:type (describe (s/enum "attach" "detach") "Whether to attach or detach the provided set of tags to the file/folder")})

(s/defschema TagSuggestQueryParams
  {:contains
   (describe String "The value fragment")

   (s/optional-key :limit)
   (describe TagSuggestLimit
             "The maximum number of suggestions to return. No limit means return all")})

(s/defschema Tag
  {:id
   (describe UUID "The service-provided UUID associated with the tag")

   :value
   (describe TagValueString "The value used to identify the tag, at most 255 characters in length")

   (s/optional-key :description)
   (describe String "The description of the purpose of the tag")})

(s/defschema TagRequest
  (describe (dissoc Tag :id) "The user tag to create."))

(s/defschema TagUpdateRequest
  (describe (->optional-param TagRequest :value) "The tag fields to update."))

(s/defschema TagList
  {:tags (describe [Tag] "A list of Tags")})

(s/defschema TagIdList
  {:tags (describe [UUID] "A list of Tag UUIDs")})

(s/defschema AttachedTagTarget
  {:id   (describe UUID "The target's UUID")
   :type (describe DataTypeEnum "The target's data type")})

(s/defschema TagDetails
  (merge Tag
         {:owner_id    (describe String "The owner of the tag")
          :public      (describe Boolean "Whether the tag is publicly accessible")
          :created_on  (describe Long "The date the tag was created in ms since the POSIX epoch")
          :modified_on (describe Long "The date the tag was last modified in ms since the POSIX epoch")}))

(s/defschema AttachedTagDetails
  (merge TagDetails
         {:targets (describe [AttachedTagTarget] "A list of targets attached to the tag")}))

(s/defschema AttachedTagsListing
  {:tags (describe [AttachedTagDetails] "A list of tags and their attached targets")})

(s/defschema ErrorResponseBadTagRequest
  (assoc ErrorResponse
         :error_code (describe (s/enum ce/ERR_ILLEGAL_ARGUMENT ce/ERR_NOT_UNIQUE) "Bad Tag Request error codes")))

(def TagDefaultErrorResponses
  {500      {:schema      ErrorResponseUnchecked
             :description "Unchecked errors"}
   :default {:schema      ErrorResponse
             :description "All other errors"}})

(def GetTagsResponses
  (merge {200 {:schema      AttachedTagsListing
               :description "Attached tags are listed in the response"}}
         TagDefaultErrorResponses))

(def DeleteTagsResponses
  (merge {200 {:schema      nil
               :description "The attached tags were successfully deleted"}}
         TagDefaultErrorResponses))

(def DeleteUserTagsResponses DeleteTagsResponses)

(def GetAttachedTagResponses
  (merge {200 {:schema      TagList
               :description "The tags are listed in the response"}}
         TagDefaultErrorResponses))

(def GetUserTagsResponses GetAttachedTagResponses)

(def PatchTags400Response {:schema      ErrorResponseBadTagRequest
                           :description "The `type` wasn't provided or had a value other than `attach` or `detach`;
                             or the request body wasn't syntactically correct"})
(def PatchTags404Response {:schema      ErrorResponseNotFound
                           :description "One of the provided tag Ids doesn't map to a tag for the authenticated user"})

(def GetTagSuggestionsResponses
  (merge {200 {:schema      TagList
               :description "zero or more suggestions were returned"}
          400 {:schema      ErrorResponseIllegalArgument
               :description "the `contains` parameter was missing or
                                the `limit` parameter was set to a something other than a non-negative number."}}
         TagDefaultErrorResponses))

(def PostTag400Response {:schema      ErrorResponseBadTagRequest
                         :description "The `value` was not unique, too long,
                             or the request body wasn't syntactically correct"})

(def DeleteTagResponses
  (merge {200 {:schema      nil
               :description "The tag was successfully deleted"}
          404 {:schema      ErrorResponseNotFound
               :description "`tag-id` wasn't a UUID of a tag owned by the authenticated user"}}
         TagDefaultErrorResponses))
