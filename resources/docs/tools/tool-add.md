This service adds a new private Tool to the DE for the requesting user.

Note that `type` is set to `executable` by default, `restricted` is always set to `true`,
and `container.network_mode` is set to `none` unless the requested `type` is `interactive`.

Configured default values will be used for the `time_limit_seconds`, `container.pids_limit`, and `container.memory_limit` fields.
The request may include a value less than the configured default if it's also greater than 0,
otherwise the default value will be used.
