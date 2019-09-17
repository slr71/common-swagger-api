(ns common-swagger-api.schema.data.tickets
  (:use [clojure-commons.error-codes]
        [common-swagger-api.schema :only [describe
                                          doc-only
                                          CommonResponses
                                          ErrorResponseUnchecked
                                          NonBlankString
                                          StandardUserQueryParams]])
  (:require [schema.core :as s]
            [common-swagger-api.schema.data :as data-schema]))

(def AddTicketSummary "Create Tickets")
(def AddTicketDocs
  "This endpoint allows creating tickets for a set of provided paths.")

(def DeleteTicketSummary "Delete Tickets")
(def DeleteTicketDocs
  "This endpoint deletes the provided set of tickets.")

(def ListTicketSummary "List Tickets")
(def ListTicketDocs
  "This endpoint lists tickets for a set of provided paths.")

(def ModeParamOptionalKey (s/optional-key :mode))
(def ModeParamValues [:read :write])
(def ModeParamDocs
  "Whether the created tickets allow `write` or `read` only access. Default is `read` only.")

(s/defschema AddTicketQueryParams
  {ModeParamOptionalKey
   (describe (apply s/enum ModeParamValues) ModeParamDocs)

   (s/optional-key :uses-limit)
   (describe Long "Sets the `uses-limit` of the created tickets, when provided")

   (s/optional-key :file-write-limit)
   (describe Long "Sets the `file-write-limit` of the created tickets, when provided (10 by default)")

   :public (describe Boolean "Whether the created tickets should be made public")

   (s/optional-key :for-job)
   (describe Boolean "Indicates whether the tickets are being created for an analysis")})

(s/defschema DeleteTicketQueryParams
  {(s/optional-key :for-job)
   (describe Boolean "Indicates whether the tickets being deleted were created for an analysis")})

(s/defschema TicketDefinition
  {:path (describe NonBlankString "The iRODS path for the ticket")
   :ticket-id (describe NonBlankString "The ID of the ticket. Usually, but not always, a UUID.")
   :download-url (describe NonBlankString "The URL for downloading the file associated with this ticket.")
   :download-page-url (describe NonBlankString "The URL for managing this ticket, getting links, seeing metadata, etc.")})

(s/defschema AddTicketResponse
  {:user
   (describe NonBlankString "The user performing the request.")

   :tickets
   (describe [TicketDefinition] "The tickets created")})

(s/defschema ListTicketsResponseMap
  {(describe s/Keyword "The iRODS data item's path")
   (describe [TicketDefinition] "The tickets for this path")})

(s/defschema ListTicketsResponse
  {:tickets
   (describe ListTicketsResponseMap "Map of tickets")})

;; used only for documentation
(s/defschema ListTicketsPathsMap
  {:/path/from/request/to/a/file/or/folder
   (describe [TicketDefinition] "The tickets for this path")})

;; used only for documentation
(s/defschema ListTicketsDocumentation
  {:tickets
   (describe [ListTicketsPathsMap] "the tickets")})

(s/defschema Tickets
  {:tickets (describe [NonBlankString] "A list of ticket IDs")})

(s/defschema DeleteTicketsResponse
  (assoc Tickets
   :user (describe NonBlankString "The user performing the request.")))

(def TicketCommonErrorCodes
  (conj data-schema/CommonErrorCodeResponses
        ERR_TOO_MANY_RESULTS
        ERR_DOES_NOT_EXIST
        ERR_NOT_A_USER))

(s/defschema AddTicketErrorResponses
  (merge ErrorResponseUnchecked
         {:error_code (apply s/enum (conj TicketCommonErrorCodes
                                          ERR_NOT_WRITEABLE))}))

(s/defschema AddTicketResponses
  (merge CommonResponses
         {200 {:schema      AddTicketResponse
               :description "Create Tickets Response."}
          500 {:schema      AddTicketErrorResponses
               :description data-schema/CommonErrorCodeDocs}}))

(s/defschema ListTicketErrorResponses
  (merge ErrorResponseUnchecked
         {:error_code (apply s/enum (conj TicketCommonErrorCodes
                                          ERR_NOT_READABLE))}))

(s/defschema ListTicketResponses
  (merge CommonResponses
         {200 {:schema      (doc-only ListTicketsResponse ListTicketsDocumentation)
               :description "List Tickets Response."}
          500 {:schema      ListTicketErrorResponses
               :description data-schema/CommonErrorCodeDocs}}))

(s/defschema DeleteTicketErrorResponses
  (merge ErrorResponseUnchecked
         {:error_code (apply s/enum (conj data-schema/CommonErrorCodeResponses
                                          ERR_NOT_WRITEABLE
                                          ERR_TICKET_DOES_NOT_EXIST
                                          ERR_NOT_A_USER))}))

(s/defschema DeleteTicketResponses
  (merge CommonResponses
         {200 {:schema      DeleteTicketsResponse
               :description "Delete Tickets Response."}
          500 {:schema      DeleteTicketErrorResponses
               :description data-schema/CommonErrorCodeDocs}}))
