This service is capable of updating high-level information of an App,
including the `version` label, and the `deleted` and `disabled` flags,
as well as just the labels within a single-step app that has already been made available for public use.
The app's name must not duplicate the name of any other app (visible to the requesting user)
under the same categories as this app.

**Note**: Although this endpoint accepts all App Group and Parameter fields within the 'groups' array,
only their `description`, `label`, and `display` (only in parameter arguments)
fields will be processed and updated by this endpoint.
