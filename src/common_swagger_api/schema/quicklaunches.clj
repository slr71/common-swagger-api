(ns common-swagger-api.schema.quicklaunches
  (:use [common-swagger-api.schema :only [describe NonBlankString]]
        [common-swagger-api.schema.analyses :only [AnalysisSubmission]])
  (:require [schema.core :as s]
            [schema-tools.core :as st])
  (:import [java.util UUID]))

(s/defschema QuickLaunch
  {:id
   (describe UUID "The UUID for the quick launch")

   :name
   (describe NonBlankString "The name of the quick launch")

   (s/optional-key :description)
   (describe String "The description of the quick launch")

   :creator
   (describe NonBlankString "The username for the creator of the quick launch")

   :app_id
   (describe UUID "The UUID for the app the quick launch is associated with")

   :app_version_id
   (describe UUID "The UUID for the app version the quick launch is associated with")

   (s/optional-key :is_public)
   (describe Boolean "Whether the quick launch is publicly available. Defaults to false")

   :submission
   AnalysisSubmission})

(s/defschema NewQuickLaunch
  (-> QuickLaunch
      (st/dissoc :id :user) ;user should be included in the request query params
      (st/optional-keys [:app_version_id])))

(s/defschema UpdateQuickLaunch
  (-> QuickLaunch
    (st/dissoc :id)
    (st/optional-keys-schema)))

(s/defschema QuickLaunchFavorite
  {:id
   (describe UUID "The UUID for the quick launch favorite entry")

   :quick_launch_id
   (describe UUID "The UUID of the quick launch that was favorited")

   :user
   (describe NonBlankString "The username of the user that favorited the quick launch")})

(s/defschema NewQuickLaunchFavorite
  (st/dissoc QuickLaunchFavorite :id :user)) ;user is in query params, id is auto-assigned.

(s/defschema QuickLaunchUserDefault
  {:id
   (describe UUID "The UUID for the quick launch user default")

   :user
   (describe NonBlankString "The username of the user that is using the quick launch as the default for the app")

   :app_id
   (describe UUID "The UUID of the app for which the user default is being set")

   :quick_launch_id
   (describe UUID "The UUID of the quick launch being used as the default for the app")})

(s/defschema NewQuickLaunchUserDefault
  (st/dissoc QuickLaunchUserDefault :id :user))

(s/defschema UpdateQuickLaunchUserDefault
  (-> QuickLaunchUserDefault
      (st/dissoc :id)
      (st/optional-keys-schema)))

(s/defschema QuickLaunchGlobalDefault
  {:id
   (describe UUID "The UUID for the quick launch global default")

   :app_id
   (describe UUID "The UUID for the app which has a global default")

   :quick_launch_id
   (describe UUID "The UUID for the quick launch that is the global default for the app")})

(s/defschema NewQuickLaunchGlobalDefault
  (st/dissoc QuickLaunchGlobalDefault :id))

(s/defschema UpdateQuickLaunchGlobalDefault
  (-> QuickLaunchGlobalDefault
      (st/dissoc :id)
      (st/optional-keys-schema)))
