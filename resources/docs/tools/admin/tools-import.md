This service adds new Tools to the DE.

#### ⚠️Warning: Use caution with Entrypoint settings!

Do not add a tool without an `entrypoint` setting if its Docker image also does not have a default `ENTRYPOINT`.
If a tool like this is required, then its `network_mode` setting should be set to `none`
to contain any risky scripts run by this tool.

#### ⚠️Warning: Use caution with Volumes settings!

Do not add `volumes` or `volumes_from` settings to tools
unless it is certain that tool is authorized to access that data.
