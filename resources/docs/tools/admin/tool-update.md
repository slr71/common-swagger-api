This service updates a Tool definition in the DE.

**Note**: If the `container` object is omitted in the request, then existing container settings will not be modified,
but if the `container` object is present in the request, then all container settings must be included in it.
Any existing settings not included in the request's `container` object will be removed.

#### ⚠️Danger Zone⚠️

Do not update container settings that are in use by tools in public apps
unless it is certain the new container settings will not break reproducibility for those apps.
If required, the `overwrite-public` flag may be used to update these settings for public tools.
