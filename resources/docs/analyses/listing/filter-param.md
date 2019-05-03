Allows results to be filtered based on the value of some result field.
The format of this parameter is
`[{"field":"some_field", "value":"search-term"}, ...]`,
where `field` is the name of the field on which the filter is based and `value` is the search value.
If `field` is `name` or `app_name`, then `value` can be contained anywhere, case-insensitive, in the corresponding field.
For example, to obtain the list of all jobs that were executed using an application with `CACE` anywhere in its name,
the parameter value can be `[{"field":"app_name","value":"cace"}]`.
To find a job with a specific `id`, the parameter value can be
`[{"field":"id","value":"C09F5907-B2A2-4429-A11E-5B96F421C3C1"}]`.
To find jobs associated with a specific `parent_id`, the parameter value can be
`[{"field":"parent_id","value":"b4c2f624-7cbd-496e-adad-5be8d0d3b941"}]`.
It's also possible to search for jobs without a parent using this parameter value:
`[{"field":"parent_id","value":null}]`.
The `ownership` field can be used to specify whether analyses that belong to the authenticated user
or analyses that are shared with the authenticated user should be listed.
If the value is `all` then all analyses that are visible to the user will be listed.
If the value is `mine` then only analyses that were submitted by the user will be listed.
If the value is `theirs` then only analyses that have been shared with the user will be listed.
By default, all analyses are listed.
The `ownership` field is the only field for which only one filter value is supported.
If multiple `ownership` field values are specified then the first value specified is used.
Here's an example: `[{"field":"ownership","value":"mine"}]`.
