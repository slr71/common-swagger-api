This service updates a private Tool definition in the DE.
As with new private Tools, `restricted` is always set to `true`,
and a configured limit may override the `time_limit_seconds` field set in the request.

**Note**: If the `container` object is omitted in the request, then existing container settings will not be modified,
but if the `container` object is present in the request, then all container settings must be included in it.
Any existing settings not included in the request's `container` object will be removed,
except `network_mode` is always set to `none` (or `bridge` for `interactive` types)
and configured limits may override values set (or omitted) for the `pids_limit` and `memory_limit` fields.
