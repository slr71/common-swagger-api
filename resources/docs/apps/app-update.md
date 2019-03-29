This service updates a single-step App in the database, as long as the App has not been submitted for public use,
and the app's name must not duplicate the name of any other app (visible to the requesting user)
under the same categories as this app.

#### Delegates to metadata service
    POST /avus/filter-targets
    GET /avus/{target-type}/{target-id}
Where `{target-type}` is `app`.
