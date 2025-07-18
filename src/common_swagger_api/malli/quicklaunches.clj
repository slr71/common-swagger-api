(ns common-swagger-api.malli.quicklaunches
  (:require [common-swagger-api.malli :as m]))

(def ExampleQualifiedParameterId "4c39cf86-d573-460a-b73b-f33f89fc70d7_e2071209-e2bb-4632-a1a6-11cd4ce2b246")
(def ExampleInputFiles ["/path/to/frag_1.fastq" "/path/to/frag_2.fastq" "/path/to/illumina_adapters.fa"])
(def ExampleSubmission
  {"config"     {ExampleQualifiedParameterId ExampleInputFiles}
   "name"       "some_analysis"
   "app_id"     "34f2c392-9a8a-11e8-9c8e-008cfa5ae621"
   "system_id"  "de"
   "debug"      false
   "output_dir" "/iplant/home/ipcdev/analyses"
   "notify"     true})

(def QuickLaunch
  [:map
   [:id
    {:description         "The UUID for the quick launch"
     :json-schema/example "ebe19202-e191-46cd-8b82-c16ac070d9b6"}
    :uuid]

   [:name
    {:description         "The name of the quick launch"
     :json-schema/example "Some Quicklaunch"}
    m/NonBlankString]

   [:description
    {:optional            true
     :description         "The description of the quick launch"
     :json-schema/example "The best quick launch ever!"}
    m/NonBlankString]

   [:creator
    {:description         "The username ofthe creator of the quick launch"
     :json-schema/example "a_user"}
    m/NonBlankString]

   [:app_id
    {:description         "The UUID of the app the quick launch is associated with"
     :json-schema/example "3de587a5-f99f-482e-be05-a0b50dc2e0cc"}
    :uuid]

   [:app_version_id
    {:description         "The UUID of the app version the quick launch is associated with"
     :json-schema/example "cdbd8c20-a451-4890-a188-1768292381e6"}
    :uuid]

   [:is_public
    {:optional            true
     :description         "Whether the quick launch is publicly available. Defaults to false"
     :json-schema/example false}
    :boolean]

   [:submission
    {:description         "The analysis submission configuration"
     :json-schema/example ExampleSubmission}
    :any]])

(def NewQuickLaunch
  (-> QuickLaunch
      (m/remove-fields :id :user)
      (m/make-fields-optional :app_version_id)))

(def UpdateQuickLaunch
  [:map
   [:name
    {:optional            true
     :description         "The name of the quick launch"
     :json-schema/example "Updated Quick Launch"}
    m/NonBlankString]

   [:description
    {:optional            true
     :description         "The description of the quick launch"
     :json-schema/example "Updated description"}
    m/NonBlankString]

   [:creator
    {:optional            true
     :description         "The username for the creator of the quick launch"
     :json-schema/example "user123"}
    m/NonBlankString]

   [:app_id
    {:optional            true
     :description         "The UUID for the app the quick launch is associated with"
     :json-schema/example "3de587a5-f99f-482e-be05-a0b50dc2e0cc"}
    :uuid]

   [:app_version_id
    {:optional            true
     :description         "The UUID for the app version the quick launch is associated with"
     :json-schema/example "cdbd8c20-a451-4890-a188-1768292381e6"}
    :uuid]

   [:is_public
    {:optional            true
     :description         "Whether the quick launch is publicly available"
     :json-schema/example true}
    :boolean]

   [:submission
    {:optional            true
     :description         "The analysis submission configuration"
     :json-schema/example {}}
    :any]])

(def QuickLaunchFavorite
  [:map
   [:id
    {:description         "The UUID for the quick launch favorite entry"
     :json-schema/example "f47ac10b-58cc-4372-a567-0e02b2c3d479"}
    :uuid]

   [:quick_launch_id
    {:description         "The UUID of the quick launch that was favorited"
     :json-schema/example "ebe19202-e191-46cd-8b82-c16ac070d9b6"}
    :uuid]

   [:user
    {:description         "The username of the user that favorited the quick launch"
     :json-schema/example "user123"}
    m/NonBlankString]])

(def NewQuickLaunchFavorite
  [:map
   [:quick_launch_id
    {:description         "The UUID of the quick launch that was favorited"
     :json-schema/example "ebe19202-e191-46cd-8b82-c16ac070d9b6"}
    :uuid]])

(def QuickLaunchUserDefault
  [:map
   [:id
    {:description         "The UUID for the quick launch user default"
     :json-schema/example "f47ac10b-58cc-4372-a567-0e02b2c3d479"}
    :uuid]

   [:user
    {:description         "The username of the user that is using the quick launch as the default for the app"
     :json-schema/example "user123"}
    m/NonBlankString]

   [:app_id
    {:description         "The UUID of the app for which the user default is being set"
     :json-schema/example "3de587a5-f99f-482e-be05-a0b50dc2e0cc"}
    :uuid]

   [:quick_launch_id
    {:description         "The UUID of the quick launch being used as the default for the app"
     :json-schema/example "ebe19202-e191-46cd-8b82-c16ac070d9b6"}
    :uuid]])

(def NewQuickLaunchUserDefault
  [:map
   [:app_id
    {:description         "The UUID of the app for which the user default is being set"
     :json-schema/example "3de587a5-f99f-482e-be05-a0b50dc2e0cc"}
    :uuid]

   [:quick_launch_id
    {:description         "The UUID of the quick launch being used as the default for the app"
     :json-schema/example "ebe19202-e191-46cd-8b82-c16ac070d9b6"}
    :uuid]])

(def UpdateQuickLaunchUserDefault
  [:map
   [:user
    {:optional            true
     :description         "The username of the user that is using the quick launch as the default for the app"
     :json-schema/example "user123"}
    m/NonBlankString]

   [:app_id
    {:optional            true
     :description         "The UUID of the app for which the user default is being set"
     :json-schema/example "3de587a5-f99f-482e-be05-a0b50dc2e0cc"}
    :uuid]

   [:quick_launch_id
    {:optional            true
     :description         "The UUID of the quick launch being used as the default for the app"
     :json-schema/example "ebe19202-e191-46cd-8b82-c16ac070d9b6"}
    :uuid]])

(def QuickLaunchGlobalDefault
  [:map
   [:id
    {:description         "The UUID for the quick launch global default"
     :json-schema/example "f47ac10b-58cc-4372-a567-0e02b2c3d479"}
    :uuid]

   [:app_id
    {:description         "The UUID for the app which has a global default"
     :json-schema/example "3de587a5-f99f-482e-be05-a0b50dc2e0cc"}
    :uuid]

   [:quick_launch_id
    {:description         "The UUID for the quick launch that is the global default for the app"
     :json-schema/example "ebe19202-e191-46cd-8b82-c16ac070d9b6"}
    :uuid]])

(def NewQuickLaunchGlobalDefault
  [:map
   [:app_id
    {:description         "The UUID for the app which has a global default"
     :json-schema/example "3de587a5-f99f-482e-be05-a0b50dc2e0cc"}
    :uuid]

   [:quick_launch_id
    {:description         "The UUID for the quick launch that is the global default for the app"
     :json-schema/example "ebe19202-e191-46cd-8b82-c16ac070d9b6"}
    :uuid]])

(def UpdateQuickLaunchGlobalDefault
  [:map
   [:app_id
    {:optional            true
     :description         "The UUID for the app which has a global default"
     :json-schema/example "3de587a5-f99f-482e-be05-a0b50dc2e0cc"}
    :uuid]

   [:quick_launch_id
    {:optional            true
     :description         "The UUID for the quick launch that is the global default for the app"
     :json-schema/example "ebe19202-e191-46cd-8b82-c16ac070d9b6"}
    :uuid]])
