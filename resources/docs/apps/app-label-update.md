This service is capable of updating just the labels within a single-step app,
and it allows apps that have already been made available for public use to be updated,
which helps to eliminate administrative thrash for app updates that only correct typographical errors.
The app's name must not duplicate the name of any other app (visible to the requesting user)
under the same categories as this app.
Upon error, the response body contains an error code along with some additional information about the error.

**Note**: Although this endpoint accepts all App fields,
only the `name` (except in parameters and parameter arguments),
`description`, `label`, and `display` (only in parameter arguments)
fields will be processed and updated by this endpoint.
