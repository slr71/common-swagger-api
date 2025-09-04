(ns common-swagger-api.malli.apps.rating
  (:require [malli.util :as mu]))

(defn UserRatingParam
  "Returns a user rating field definition with the given field name"
  [field-name]
  [field-name
   {:description         "The current user's rating for this App"
    :json-schema/example 5}
   :long])

(defn CommentIdParam
  "Returns a comment ID field definition with the given field name"
  [field-name]
  [field-name
   {:description         "The ID of the current user's rating comment for this App"
    :json-schema/example 123}
   :long])

(def RatingResponse
  [:map {:closed true}
   [:average
    {:description         "The average user rating for this App"
     :json-schema/example 4.5}
    :double]

   [:total
    {:description         "The total number of user ratings for this App"
     :json-schema/example 42}
    :long]])

(def Rating
  (mu/merge RatingResponse
    [:map
     (mu/optional (UserRatingParam :user))
     (mu/optional (CommentIdParam :comment_id))]))

(def RatingRequest
  [:map {:closed true
         :description "The user's new rating for this App."}
   (UserRatingParam :rating)
   (mu/optional (CommentIdParam :comment_id))])
