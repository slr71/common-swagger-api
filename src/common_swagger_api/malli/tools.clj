(ns common-swagger-api.malli.tools
  (:require [common-swagger-api.malli :refer [PagingParams]]
            [common-swagger-api.malli.common :refer [IncludeHiddenParams]]
            [common-swagger-api.malli.containers :refer [NewToolContainer ToolContainer]]))

(def ToolSearchParams
  [:map
   IncludeHiddenParams
   PagingParams
   [:search
    {:optional            true
     :description         "The pattern to match in a Tool's Name or Description"
     :json-schema/example "blast"}
    :string]
   [:public
    {:optional            true
     :description         "Set to `true` to list only public Tools, `false` to list only private Tools, or leave unset to list all Tools"
     :json-schema/example true}
    :boolean]])

(def ToolDetailsParams
  [:map
   [:include-defaults
    {:optional            true
     :description         "Flag to include defaults set by configuration or not"
     :json-schema/example false}
    :boolean]])

(def PrivateToolDeleteParams
  [:map
   [:force-delete
    {:optional            true
     :description         "Flag to force deletion of a Tool already in use by Apps"
     :json-schema/example false}
    :boolean]])

(def ToolTestData
  [:map
   [:params
    {:optional            true
     :description         "The list of command-line parameters"
     :json-schema/example ["-i", "input.txt"]}
    [:vector :string]]
   [:input_files
    {:description         "The list of paths to test input files in iRODS"
     :json-schema/example ["/iplant/home/user/input.txt"]}
    [:vector :string]]
   [:output_files
    {:description         "The list of paths to expected output files in iRODS"
     :json-schema/example ["/iplant/home/user/output.txt"]}
    [:vector :string]]])

(def ToolImplementor
  [:map
   [:implementor
    {:description         "The name of the implementor"
     :json-schema/example "John Doe"}
    :string]
   [:implementor_email
    {:description         "The email address of the implementor"
     :json-schema/example "john.doe@example.com"}
    :string]])

(def ToolImplementation
  [:map
   ToolImplementor
   [:test
    {:description         "The test data for the Tool"
     :json-schema/example {}}
    ToolTestData]])

(def Tool
  [:map
   [:id
    {:description         "A UUID that is used to identify the Tool"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:name
    {:description         "The Tool's name (can be the file name or Docker image)"
     :json-schema/example "BLAST"}
    :string]
   [:description
    {:optional            true
     :description         "A brief description of the Tool"
     :json-schema/example "Basic Local Alignment Search Tool"}
    :string]
   [:attribution
    {:optional            true
     :description         "The Tool's author or publisher"
     :json-schema/example "NCBI"}
    :string]
   [:location
    {:optional            true
     :description         "The path of the directory containing the Tool"
     :json-schema/example "/usr/local/bin"}
    :string]
   [:version
    {:description         "The Tool's version"
     :json-schema/example "2.10.1"}
    :string]
   [:type
    {:description         "The Tool Type name"
     :json-schema/example "executable"}
    :string]
   [:restricted
    {:optional            true
     :description         "Determines whether a time limit is applied and whether network access is granted"
     :json-schema/example false}
    :boolean]
   [:time_limit_seconds
    {:optional            true
     :description         "The number of seconds that a tool is allowed to execute. A value of 0 means the time limit is disabled"
     :json-schema/example 3600}
    :int]
   [:interactive
    {:optional            true
     :description         "Determines whether the tool is interactive"
     :json-schema/example false}
    :boolean]])

(def ToolDetails
  [:map
   Tool
   [:is_public
    {:description         "Whether the Tool has been published and is viewable by all users"
     :json-schema/example true}
    :boolean]
   [:permission
    {:description         "The user's access level for the Tool"
     :json-schema/example "read"}
    :string]
   [:implementation
    {:description         "Information about the user who integrated the Tool into the DE"
     :json-schema/example {}}
    ToolImplementation]
   [:container
    {:description         "Container information for the tool"
     :json-schema/example {}}
    ToolContainer]])

(def ToolImportRequest
  [:map
   [:id
    {:optional            true
     :description         "A UUID that is used to identify the Tool"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:name
    {:description         "The Tool's name (can be the file name or Docker image)"
     :json-schema/example "BLAST"}
    :string]
   [:description
    {:optional            true
     :description         "A brief description of the Tool"
     :json-schema/example "Basic Local Alignment Search Tool"}
    :string]
   [:attribution
    {:optional            true
     :description         "The Tool's author or publisher"
     :json-schema/example "NCBI"}
    :string]
   [:location
    {:optional            true
     :description         "The path of the directory containing the Tool"
     :json-schema/example "/usr/local/bin"}
    :string]
   [:version
    {:description         "The Tool's version"
     :json-schema/example "2.10.1"}
    :string]
   [:type
    {:description         "The Tool Type name"
     :json-schema/example "executable"}
    :string]
   [:restricted
    {:optional            true
     :description         "Determines whether a time limit is applied and whether network access is granted"
     :json-schema/example false}
    :boolean]
   [:time_limit_seconds
    {:optional            true
     :description         "The number of seconds that a tool is allowed to execute. A value of 0 means the time limit is disabled"
     :json-schema/example 3600}
    :int]
   [:interactive
    {:optional            true
     :description         "Determines whether the tool is interactive"
     :json-schema/example false}
    :boolean]
   [:implementation
    {:description         "Information about the user who integrated the Tool into the DE"
     :json-schema/example {}}
    ToolImplementation]
   [:container
    {:description         "Container information for the tool"
     :json-schema/example {}}
    NewToolContainer]])

(def PrivateToolImportRequest
  [:map
   [:id
    {:optional            true
     :description         "A UUID that is used to identify the Tool"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:name
    {:description         "The Tool's name (can be the file name or Docker image)"
     :json-schema/example "BLAST"}
    :string]
   [:description
    {:optional            true
     :description         "A brief description of the Tool"
     :json-schema/example "Basic Local Alignment Search Tool"}
    :string]
   [:attribution
    {:optional            true
     :description         "The Tool's author or publisher"
     :json-schema/example "NCBI"}
    :string]
   [:location
    {:optional            true
     :description         "The path of the directory containing the Tool"
     :json-schema/example "/usr/local/bin"}
    :string]
   [:version
    {:description         "The Tool's version"
     :json-schema/example "2.10.1"}
    :string]
   [:type
    {:optional            true
     :description         "The Tool Type name"
     :json-schema/example "executable"}
    :string]
   [:restricted
    {:optional            true
     :description         "Determines whether a time limit is applied and whether network access is granted"
     :json-schema/example false}
    :boolean]
   [:time_limit_seconds
    {:optional            true
     :description         "The number of seconds that a tool is allowed to execute. A value of 0 means the time limit is disabled"
     :json-schema/example 3600}
    :int]
   [:interactive
    {:optional            true
     :description         "Determines whether the tool is interactive"
     :json-schema/example false}
    :boolean]
   [:implementation
    {:optional            true
     :description         "Information about the user who integrated the Tool into the DE"
     :json-schema/example {}}
    ToolImplementation]
   [:container
    {:description         "Container information for the tool"
     :json-schema/example {}}
    :any]]) ; Would be PrivateToolContainerImportRequest but simplified

(def PrivateToolUpdateRequest
  [:map
   [:id
    {:optional            true
     :description         "A UUID that is used to identify the Tool"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:name
    {:optional            true
     :description         "The Tool's name (can be the file name or Docker image)"
     :json-schema/example "BLAST"}
    :string]
   [:description
    {:optional            true
     :description         "A brief description of the Tool"
     :json-schema/example "Basic Local Alignment Search Tool"}
    :string]
   [:attribution
    {:optional            true
     :description         "The Tool's author or publisher"
     :json-schema/example "NCBI"}
    :string]
   [:location
    {:optional            true
     :description         "The path of the directory containing the Tool"
     :json-schema/example "/usr/local/bin"}
    :string]
   [:version
    {:optional            true
     :description         "The Tool's version"
     :json-schema/example "2.10.1"}
    :string]
   [:type
    {:optional            true
     :description         "The Tool Type name"
     :json-schema/example "executable"}
    :string]
   [:restricted
    {:optional            true
     :description         "Determines whether a time limit is applied and whether network access is granted"
     :json-schema/example false}
    :boolean]
   [:time_limit_seconds
    {:optional            true
     :description         "The number of seconds that a tool is allowed to execute. A value of 0 means the time limit is disabled"
     :json-schema/example 3600}
    :int]
   [:interactive
    {:optional            true
     :description         "Determines whether the tool is interactive"
     :json-schema/example false}
    :boolean]
   [:implementation
    {:optional            true
     :description         "Information about the user who integrated the Tool into the DE"
     :json-schema/example {}}
    ToolImplementation]
   [:container
    {:optional            true
     :description         "Container information for the tool"
     :json-schema/example {}}
    :any]]) ; Would be PrivateToolContainerImportRequest but simplified

(def ToolRequestStatus
  [:map
   [:status
    {:optional            true
     :description         "The status code of the Tool Request update. The status code is case-sensitive, and if it isn't defined in the database already then it will be added to the list of known status codes"
     :json-schema/example "Submitted"}
    :string]
   [:status_date
    {:description         "The timestamp of the Tool Request status update"
     :json-schema/example 1634567890000}
    :int]
   [:updated_by
    {:description         "The username of the user that updated the Tool Request status"
     :json-schema/example "admin"}
    :string]
   [:comments
    {:optional            true
     :description         "The administrator comments of the Tool Request status update"
     :json-schema/example "Tool approved for installation"}
    :string]])

(def ToolRequestDetails
  [:map
   [:id
    {:description         "The Tool Request's UUID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:submitted_by
    {:description         "The username of the user that submitted the Tool Request"
     :json-schema/example "user123"}
    :string]
   [:phone
    {:optional            true
     :description         "The phone number of the user submitting the request"
     :json-schema/example "+1-555-123-4567"}
    :string]
   [:tool_id
    {:optional            true
     :description         "The ID of the tool the user is requesting to be made public"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440001"}
    :uuid]
   [:name
    {:description         "The Tool's name (can be the file name or Docker image)"
     :json-schema/example "BLAST"}
    :string]
   [:description
    {:description         "A brief description of the Tool"
     :json-schema/example "Basic Local Alignment Search Tool"}
    :string]
   [:source_url
    {:optional            true
     :description         "A link that can be used to obtain the tool"
     :json-schema/example "https://github.com/ncbi/blast"}
    :string]
   [:source_upload_file
    {:optional            true
     :description         "The path to a file that has been uploaded into iRODS"
     :json-schema/example "/iplant/home/user/blast.tar.gz"}
    :string]
   [:documentation_url
    {:description         "A link to the tool documentation"
     :json-schema/example "https://blast.ncbi.nlm.nih.gov/doc/"}
    :string]
   [:version
    {:description         "The Tool's version"
     :json-schema/example "2.10.1"}
    :string]
   [:attribution
    {:optional            true
     :description         "The Tool's author or publisher"
     :json-schema/example "NCBI"}
    :string]
   [:multithreaded
    {:optional            true
     :description         "A flag indicating whether or not the tool is multithreaded"
     :json-schema/example true}
    :boolean]
   [:test_data_path
    {:description         "The path to a test data file that has been uploaded to iRODS"
     :json-schema/example "/iplant/home/user/test_data.txt"}
    :string]
   [:cmd_line
    {:description         "Instructions for using the tool"
     :json-schema/example "blast -i input.txt -o output.txt"}
    :string]
   [:additional_info
    {:optional            true
     :description         "Any additional information that may be helpful during tool installation or validation"
     :json-schema/example "This tool requires at least 4GB of RAM"}
    :string]
   [:additional_data_file
    {:optional            true
     :description         "Any additional data file that may be helpful during tool installation or validation"
     :json-schema/example "/iplant/home/user/additional_data.txt"}
    :string]
   [:architecture
    {:optional            true
     :description         "The architecture for the tool"
     :json-schema/example "64-bit Generic"}
    [:enum "32-bit Generic" "64-bit Generic" "Others" "Don't know"]]
   [:history
    {:description         "A history of status updates for this Tool Request"
     :json-schema/example []}
    [:vector ToolRequestStatus]]
   [:interactive
    {:optional            true
     :description         "Determines whether the tool is interactive"
     :json-schema/example false}
    :boolean]])

(def ToolRequest
  [:map
   [:phone
    {:optional            true
     :description         "The phone number of the user submitting the request"
     :json-schema/example "+1-555-123-4567"}
    :string]
   [:tool_id
    {:optional            true
     :description         "The ID of the tool the user is requesting to be made public"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440001"}
    :uuid]
   [:name
    {:description         "The Tool's name (can be the file name or Docker image)"
     :json-schema/example "BLAST"}
    :string]
   [:description
    {:description         "A brief description of the Tool"
     :json-schema/example "Basic Local Alignment Search Tool"}
    :string]
   [:source_url
    {:optional            true
     :description         "A link that can be used to obtain the tool"
     :json-schema/example "https://github.com/ncbi/blast"}
    :string]
   [:source_upload_file
    {:optional            true
     :description         "The path to a file that has been uploaded into iRODS"
     :json-schema/example "/iplant/home/user/blast.tar.gz"}
    :string]
   [:documentation_url
    {:description         "A link to the tool documentation"
     :json-schema/example "https://blast.ncbi.nlm.nih.gov/doc/"}
    :string]
   [:version
    {:description         "The Tool's version"
     :json-schema/example "2.10.1"}
    :string]
   [:attribution
    {:optional            true
     :description         "The Tool's author or publisher"
     :json-schema/example "NCBI"}
    :string]
   [:multithreaded
    {:optional            true
     :description         "A flag indicating whether or not the tool is multithreaded"
     :json-schema/example true}
    :boolean]
   [:test_data_path
    {:description         "The path to a test data file that has been uploaded to iRODS"
     :json-schema/example "/iplant/home/user/test_data.txt"}
    :string]
   [:cmd_line
    {:description         "Instructions for using the tool"
     :json-schema/example "blast -i input.txt -o output.txt"}
    :string]
   [:additional_info
    {:optional            true
     :description         "Any additional information that may be helpful during tool installation or validation"
     :json-schema/example "This tool requires at least 4GB of RAM"}
    :string]
   [:additional_data_file
    {:optional            true
     :description         "Any additional data file that may be helpful during tool installation or validation"
     :json-schema/example "/iplant/home/user/additional_data.txt"}
    :string]
   [:architecture
    {:optional            true
     :description         "The architecture for the tool"
     :json-schema/example "64-bit Generic"}
    [:enum "32-bit Generic" "64-bit Generic" "Others" "Don't know"]]
   [:interactive
    {:optional            true
     :description         "Determines whether the tool is interactive"
     :json-schema/example false}
    :boolean]])

(def ToolRequestSummary
  [:map
   [:id
    {:description         "The Tool Request's UUID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:name
    {:description         "The Tool's name (can be the file name or Docker image)"
     :json-schema/example "BLAST"}
    :string]
   [:version
    {:description         "The Tool's version"
     :json-schema/example "2.10.1"}
    :string]
   [:requested_by
    {:description         "The username of the user that submitted the Tool Request"
     :json-schema/example "user123"}
    :string]
   [:tool_id
    {:optional            true
     :description         "The ID of the tool the user is requesting to be made public"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440001"}
    :uuid]
   [:date_submitted
    {:description         "The timestamp of the Tool Request submission"
     :json-schema/example 1634567890000}
    :int]
   [:status
    {:description         "The current status of the Tool Request"
     :json-schema/example "Submitted"}
    :string]
   [:date_updated
    {:description         "The timestamp of the last Tool Request status update"
     :json-schema/example 1634567890000}
    :int]
   [:updated_by
    {:description         "The username of the user that last updated the Tool Request status"
     :json-schema/example "admin"}
    :string]])

(def ToolRequestListing
  [:map
   [:tool_requests
    {:description         "A listing of high level details about tool requests that have been submitted"
     :json-schema/example []}
    [:vector ToolRequestSummary]]])

(def ToolRequestListingParams
  [:map
   PagingParams
   [:status
    {:optional            true
     :description         "The name of a status code to include in the results"
     :json-schema/example "Submitted"}
    :string]])

(def ToolRequestStatusCodeListingParams
  [:map
   [:filter
    {:optional            true
     :description         "If this parameter is set then only the status codes that contain the string passed in this query parameter will be listed"
     :json-schema/example "submit"}
    :string]])

(def ToolRequestStatusCode
  [:map
   [:id
    {:description         "The Status Code's UUID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:name
    {:description         "The Status Code"
     :json-schema/example "Submitted"}
    :string]
   [:description
    {:description         "A brief description of the Status Code"
     :json-schema/example "The tool request has been submitted"}
    :string]])

(def ToolRequestStatusCodeListing
  [:map
   [:status_codes
    {:description         "A listing of known Status Codes"
     :json-schema/example []}
    [:vector ToolRequestStatusCode]]])

(def ToolListing
  [:map
   [:tools
    {:description         "Listing of App Tools"
     :json-schema/example []}
    [:vector :any]] ; Would be ToolListingItem but simplified for now
   [:total
    {:description         "The total number of App Tools in the listing"
     :json-schema/example 42}
    :int]])
