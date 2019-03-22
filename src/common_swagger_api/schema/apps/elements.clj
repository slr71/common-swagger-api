(ns common-swagger-api.schema.apps.elements
  (:use [common-swagger-api.schema :only [describe]]
        [schema.core :only [defschema optional-key]])
  (:import [java.util UUID]))

(def AppElementsListingSummary "List All Available App Elements")
(def AppElementsListingDocs
  "This endpoint may be used to obtain lists of all available elements that may be included in an App.")

(def AppElementsDataSourceListingSummary "List App File Parameter Data Sources")
(def AppElementsDataSourceListingDocs
  "Data sources are the known possible sources for file parameters.
   In most cases, file parameters will come from a plain file.
   The only other options that are currently available are redirected standard output and redirected standard error output.
   Both of these options apply only to file parameters that are associated with an output.")

(def AppElementsFileFormatListingSummary "List App Parameter File Formats")
(def AppElementsFileFormatListingDocs
  "The known file formats can be used to describe supported input or output formats for a tool.
   For example, tools in the FASTX toolkit may support FASTA files,
   several different varieties of FASTQ files and Barcode files,
   among others.")

(def AppElementsInfoTypeListingSummary "List Tool Info Types")
(def AppElementsInfoTypeListingDocs
  "The known information types can be used to describe the type of information consumed or produced by a tool.
   This is distinct from the data format because some data formats may contain multiple types of information
   and some types of information can be described using multiple data formats.
   For example, the Nexus format can contain multiple types of information, including phylogenetic trees.
   And phylogenetic trees can also be represented in PhyloXML format, and a large number of other formats.
   The file format and information type together identify the type of input consumed by a tool
   or the type of output produced by a tool.")

(def AppElementsParameterTypeListingSummary "List App Parameter Types")
(def AppElementsParameterTypeListingDocs
  "Parameter types represent the types of information that can be passed to a tool.
   For command-line tools, a parameter generally represents a command-line option
   and the parameter type represents the type of data required by the command-line option.
   For example a `Boolean` parameter generally corresponds to a single command-line flag that takes no arguments.
   A `Text` parameter, on the other hand, generally represents some sort of textual information.
   Some parameter types are not supported by all tool types,
   so it is helpful in some cases to filter parameter types either by the tool type or optionally by the tool
   (which is used to determine the tool type).
   If you filter by both tool type and tool ID then the tool type will take precedence.
   Including either an undefined tool type or an undefined tool type name will result in an error.")

(def AppElementsRuleTypeListingSummary "List App Parameter Rule Types")
(def AppElementsRuleTypeListingDocs
  "Rule types represent types of validation rules that may be defined to validate user input.
   For example, if a parameter value must be an integer between 1 and 10 then the `IntRange` rule type may be used.
   Similarly, if a parameter value must contain data in a specific format, such as a phone number,
   then the `Regex` rule type may be used.")

(def AppElementsToolTypeListingSummary "List App Tool Types")
(def AppElementsToolTypeListingDocs
  "Tool types are known types of tools in the Discovery Environment.
   Generally, there's a different tool type for each execution environment that is supported by the DE.")

(def AppElementsValueTypeListingSummary "List App Parameter and Rule Value Types")
(def AppElementsValueTypeListingDocs
  "If you look closely at the response schema for parameter types and rule types listings
   then you'll notice that each parameter type has a single value type assocaited with it
   and each rule type has one or more value types associated with it.
   The purpose of value types is specifically to link parameter types and rule types.
   The App Editor uses the value type to determine which types of rules can be applied to a parameter
   that is being defined by the user.")

(defschema AppParameterTypeParams
  {(optional-key :tool-type) (describe String "Filters results by tool type")
   (optional-key :tool-id)   (describe UUID "Filters results by tool identifier")})

(defschema DataSource
  {:id          (describe UUID "A UUID that is used to identify the Data Source")
   :name        (describe String "The Data Source's name")
   :description (describe String "The Data Source's description")
   :label       (describe String "The Data Source's label")})

(defschema FileFormat
  {:id                   (describe UUID "A UUID that is used to identify the File Format")
   :name                 (describe String "The File Format's name")
   (optional-key :label) (describe String "The File Format's label")})

(defschema InfoType
  {:id                   (describe UUID "A UUID that is used to identify the Info Type")
   :name                 (describe String "The Info Type's name")
   (optional-key :label) (describe String "The Info Type's label")})

(defschema ParameterType
  {:id                         (describe UUID "A UUID that is used to identify the Parameter Type")
   :name                       (describe String "The Parameter Type's name")
   (optional-key :description) (describe String "The Parameter Type's description")
   :value_type                 (describe String "The Parameter Type's value type name")})

(defschema RuleType
  {:id                         (describe UUID "A UUID that is used to identify the Rule Type")
   :name                       (describe String "The Rule Type's name")
   (optional-key :description) (describe String "The Rule Type's description")
   :rule_description_format    (describe String "The Rule Type's description format")
   :subtype                    (describe String "The Rule Type's subtype")
   :value_types                (describe [String] "The Rule Type's value types")})

(defschema ToolType
  {:id                         (describe UUID "A UUID that is used to identify the Tool Type")
   :name                       (describe String "The Tool Type's name")
   (optional-key :description) (describe String "The Tool Type's description")
   :label                      (describe String "The Tool Type's label")})

(defschema ValueType
  {:id          (describe UUID "A UUID that is used to identify the Value Type")
   :name        (describe String "The Value Type's name")
   :description (describe String "The Value Type's description")})

(defschema DataSourceListing
  {:data_sources (describe [DataSource] "Listing of App File Parameter Data Sources")})

(defschema FileFormatListing
  {:formats (describe [FileFormat] "Listing of App Parameter File Formats")})

(defschema InfoTypeListing
  {:info_types (describe [InfoType] "Listing of Tool Info Types")})

(defschema ParameterTypeListing
  {:parameter_types (describe [ParameterType] "Listing of App Parameter Types")})

(defschema RuleTypeListing
  {:rule_types (describe [RuleType] "Listing of App Parameter Rule Types")})

(defschema ToolTypeListing
  {:tool_types (describe [ToolType] "Listing of App Tool Types")})

(defschema ValueTypeListing
  {:value_types (describe [ValueType] "Listing of App Parameter and Rule Value Types")})
