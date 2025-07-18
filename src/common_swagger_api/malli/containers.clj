(ns common-swagger-api.malli.containers)

(def Image
  [:map
   [:name
    {:description         "The name of the container image"
     :json-schema/example "ubuntu"}
    :string]
   [:id
    {:description         "The UUID of the image"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:tag
    {:optional            true
     :description         "The tag of the container image"
     :json-schema/example "latest"}
    :string]
   [:url
    {:optional            true
     :description         "The URL of the container image"
     :json-schema/example "https://registry.hub.docker.com/ubuntu"}
    [:maybe :string]]
   [:deprecated
    {:optional            true
     :description         "Whether the image is deprecated"
     :json-schema/example false}
    :boolean]
   [:auth
    {:optional            true
     :description         "Authentication information for the image"
     :json-schema/example nil}
    [:maybe :string]]
   [:osg_image_path
    {:optional            true
     :description         "The OSG image path"
     :json-schema/example nil}
    [:maybe :string]]])

(def NewImage
  [:map
   [:name
    {:description         "The name of the container image"
     :json-schema/example "ubuntu"}
    :string]
   [:tag
    {:optional            true
     :description         "The tag of the container image"
     :json-schema/example "latest"}
    :string]
   [:url
    {:optional            true
     :description         "The URL of the container image"
     :json-schema/example "https://registry.hub.docker.com/ubuntu"}
    [:maybe :string]]
   [:deprecated
    {:optional            true
     :description         "Whether the image is deprecated"
     :json-schema/example false}
    :boolean]
   [:auth
    {:optional            true
     :description         "Authentication information for the image"
     :json-schema/example nil}
    [:maybe :string]]
   [:osg_image_path
    {:optional            true
     :description         "The OSG image path"
     :json-schema/example nil}
    [:maybe :string]]])

(def Settings
  [:map
   [:cpu_shares
    {:optional            true
     :description         "The shares of the CPU that the tool container will receive"
     :json-schema/example 1024}
    :int]
   [:pids_limit
    {:optional            true
     :description         "The process limit for the container"
     :json-schema/example 1000}
    :int]
   [:memory_limit
    {:optional            true
     :description         "The amount of memory (in bytes) that the tool container is restricted to"
     :json-schema/example 1073741824}
    :int]
   [:min_memory_limit
    {:optional            true
     :description         "The minimum amount of memory (in bytes) that is required to run the tool container"
     :json-schema/example 536870912}
    :int]
   [:min_cpu_cores
    {:optional            true
     :description         "The minimum number of CPU cores needed to run the tool container"
     :json-schema/example 1.0}
    :double]
   [:max_cpu_cores
    {:optional            true
     :description         "The maximum number of CPU cores allowed when running the tool container"
     :json-schema/example 4.0}
    :double]
   [:min_disk_space
    {:optional            true
     :description         "The minimum amount of disk space needed to run the tool container"
     :json-schema/example 1073741824}
    :int]
   [:network_mode
    {:optional            true
     :description         "The network mode for the tool container"
     :json-schema/example "bridge"}
    :string]
   [:working_directory
    {:optional            true
     :description         "The working directory in the tool container"
     :json-schema/example "/work"}
    :string]
   [:name
    {:optional            true
     :description         "The name given to the tool container"
     :json-schema/example "my-tool-container"}
    :string]
   [:entrypoint
    {:optional            true
     :description         "The entrypoint for a tool container"
     :json-schema/example "/bin/bash"}
    :string]
   [:skip_tmp_mount
    {:optional            true
     :description         "Whether to skip mounting the tmp directory"
     :json-schema/example false}
    :boolean]
   [:uid
    {:optional            true
     :description         "The user ID to run the container as"
     :json-schema/example 1000}
    :int]
   [:id
    {:description         "The UUID of the settings"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]])

(def NewSettings
  [:map
   [:cpu_shares
    {:optional            true
     :description         "The shares of the CPU that the tool container will receive"
     :json-schema/example 1024}
    :int]
   [:pids_limit
    {:optional            true
     :description         "The process limit for the container"
     :json-schema/example 1000}
    :int]
   [:memory_limit
    {:optional            true
     :description         "The amount of memory (in bytes) that the tool container is restricted to"
     :json-schema/example 1073741824}
    :int]
   [:min_memory_limit
    {:optional            true
     :description         "The minimum amount of memory (in bytes) that is required to run the tool container"
     :json-schema/example 536870912}
    :int]
   [:min_cpu_cores
    {:optional            true
     :description         "The minimum number of CPU cores needed to run the tool container"
     :json-schema/example 1.0}
    :double]
   [:max_cpu_cores
    {:optional            true
     :description         "The maximum number of CPU cores allowed when running the tool container"
     :json-schema/example 4.0}
    :double]
   [:min_disk_space
    {:optional            true
     :description         "The minimum amount of disk space needed to run the tool container"
     :json-schema/example 1073741824}
    :int]
   [:network_mode
    {:optional            true
     :description         "The network mode for the tool container"
     :json-schema/example "bridge"}
    :string]
   [:working_directory
    {:optional            true
     :description         "The working directory in the tool container"
     :json-schema/example "/work"}
    :string]
   [:name
    {:optional            true
     :description         "The name given to the tool container"
     :json-schema/example "my-tool-container"}
    :string]
   [:entrypoint
    {:optional            true
     :description         "The entrypoint for a tool container"
     :json-schema/example "/bin/bash"}
    :string]
   [:skip_tmp_mount
    {:optional            true
     :description         "Whether to skip mounting the tmp directory"
     :json-schema/example false}
    :boolean]
   [:uid
    {:optional            true
     :description         "The user ID to run the container as"
     :json-schema/example 1000}
    :int]
   [:id
    {:optional            true
     :description         "The UUID of the settings"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]])

(def Device
  [:map
   [:host_path
    {:description         "A device's path on the container host"
     :json-schema/example "/dev/nvidia0"}
    :string]
   [:container_path
    {:description         "A device's path inside the tool container"
     :json-schema/example "/dev/nvidia0"}
    :string]
   [:id
    {:description         "The UUID of the device"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]])

(def NewDevice
  [:map
   [:host_path
    {:description         "A device's path on the container host"
     :json-schema/example "/dev/nvidia0"}
    :string]
   [:container_path
    {:description         "A device's path inside the tool container"
     :json-schema/example "/dev/nvidia0"}
    :string]])

(def Volume
  [:map
   [:host_path
    {:description         "The path to a bind mounted volume on the host machine"
     :json-schema/example "/host/data"}
    :string]
   [:container_path
    {:description         "The path to a bind mounted volume in the tool container"
     :json-schema/example "/container/data"}
    :string]
   [:id
    {:description         "The UUID of the volume"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]])

(def NewVolume
  [:map
   [:host_path
    {:description         "The path to a bind mounted volume on the host machine"
     :json-schema/example "/host/data"}
    :string]
   [:container_path
    {:description         "The path to a bind mounted volume in the tool container"
     :json-schema/example "/container/data"}
    :string]])

(def DataContainer
  [:map
   [:id
    {:description         "The UUID of the data container"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:name
    {:description         "The name of the container image"
     :json-schema/example "ubuntu"}
    :string]
   [:tag
    {:optional            true
     :description         "The tag of the container image"
     :json-schema/example "latest"}
    :string]
   [:url
    {:optional            true
     :description         "The URL of the container image"
     :json-schema/example "https://registry.hub.docker.com/ubuntu"}
    [:maybe :string]]
   [:deprecated
    {:optional            true
     :description         "Whether the image is deprecated"
     :json-schema/example false}
    :boolean]
   [:auth
    {:optional            true
     :description         "Authentication information for the image"
     :json-schema/example nil}
    [:maybe :string]]
   [:osg_image_path
    {:optional            true
     :description         "The OSG image path"
     :json-schema/example nil}
    [:maybe :string]]
   [:name_prefix
    {:description         "The name prefix for the data container"
     :json-schema/example "data-"}
    :string]
   [:read_only
    {:optional            true
     :description         "Whether the data container is read-only"
     :json-schema/example false}
    :boolean]])

(def VolumesFrom
  DataContainer)

(def NewVolumesFrom
  [:map
   [:name
    {:description         "The name of the container image"
     :json-schema/example "ubuntu"}
    :string]
   [:tag
    {:optional            true
     :description         "The tag of the container image"
     :json-schema/example "latest"}
    :string]
   [:url
    {:optional            true
     :description         "The URL of the container image"
     :json-schema/example "https://registry.hub.docker.com/ubuntu"}
    [:maybe :string]]
   [:deprecated
    {:optional            true
     :description         "Whether the image is deprecated"
     :json-schema/example false}
    :boolean]
   [:auth
    {:optional            true
     :description         "Authentication information for the image"
     :json-schema/example nil}
    [:maybe :string]]
   [:osg_image_path
    {:optional            true
     :description         "The OSG image path"
     :json-schema/example nil}
    [:maybe :string]]
   [:name_prefix
    {:description         "The name prefix for the data container"
     :json-schema/example "data-"}
    :string]
   [:read_only
    {:optional            true
     :description         "Whether the data container is read-only"
     :json-schema/example false}
    :boolean]])

(def Port
  [:map
   [:id
    {:description         "The UUID of the port"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:host_port
    {:optional            true
     :description         "The host port"
     :json-schema/example 8080}
    [:maybe :int]]
   [:container_port
    {:description         "The container port"
     :json-schema/example 80}
    :int]
   [:bind_to_host
    {:optional            true
     :description         "Whether to bind to host"
     :json-schema/example true}
    [:maybe :boolean]]])

(def NewPort
  [:map
   [:host_port
    {:optional            true
     :description         "The host port"
     :json-schema/example 8080}
    [:maybe :int]]
   [:container_port
    {:description         "The container port"
     :json-schema/example 80}
    :int]
   [:bind_to_host
    {:optional            true
     :description         "Whether to bind to host"
     :json-schema/example true}
    [:maybe :boolean]]])

(def ProxySettings
  [:map
   [:id
    {:description         "The UUID of the proxy settings"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:image
    {:description         "The proxy image"
     :json-schema/example "nginx"}
    :string]
   [:name
    {:description         "The proxy name"
     :json-schema/example "nginx-proxy"}
    :string]
   [:frontend_url
    {:optional            true
     :description         "The frontend URL"
     :json-schema/example "https://example.com"}
    [:maybe :string]]
   [:cas_url
    {:optional            true
     :description         "The CAS URL"
     :json-schema/example "https://cas.example.com"}
    [:maybe :string]]
   [:cas_validate
    {:optional            true
     :description         "The CAS validation URL"
     :json-schema/example "https://cas.example.com/validate"}
    [:maybe :string]]
   [:ssl_cert_path
    {:optional            true
     :description         "The SSL certificate path"
     :json-schema/example "/etc/ssl/certs/cert.pem"}
    [:maybe :string]]
   [:ssl_key_path
    {:optional            true
     :description         "The SSL key path"
     :json-schema/example "/etc/ssl/private/key.pem"}
    [:maybe :string]]])

(def NewProxySettings
  [:map
   [:image
    {:description         "The proxy image"
     :json-schema/example "nginx"}
    :string]
   [:name
    {:description         "The proxy name"
     :json-schema/example "nginx-proxy"}
    :string]
   [:frontend_url
    {:optional            true
     :description         "The frontend URL"
     :json-schema/example "https://example.com"}
    [:maybe :string]]
   [:cas_url
    {:optional            true
     :description         "The CAS URL"
     :json-schema/example "https://cas.example.com"}
    [:maybe :string]]
   [:cas_validate
    {:optional            true
     :description         "The CAS validation URL"
     :json-schema/example "https://cas.example.com/validate"}
    [:maybe :string]]
   [:ssl_cert_path
    {:optional            true
     :description         "The SSL certificate path"
     :json-schema/example "/etc/ssl/certs/cert.pem"}
    [:maybe :string]]
   [:ssl_key_path
    {:optional            true
     :description         "The SSL key path"
     :json-schema/example "/etc/ssl/private/key.pem"}
    [:maybe :string]]])

(def ToolContainer
  [:map
   Settings
   [:container_devices
    {:optional            true
     :description         "A list of devices associated with a tool's container"
     :json-schema/example []}
    [:vector Device]]
   [:container_volumes
    {:optional            true
     :description         "A list of volumes associated with a tool's container"
     :json-schema/example []}
    [:vector Volume]]
   [:container_volumes_from
    {:optional            true
     :description         "The list of VolumeFroms associated with a tool's container"
     :json-schema/example []}
    [:vector VolumesFrom]]
   [:container_ports
    {:optional            true
     :description         "A list of ports associated with a tool's container"
     :json-schema/example []}
    [:vector Port]]
   [:interactive_apps
    {:optional            true
     :description         "Interactive app settings for the reverse proxy"
     :json-schema/example {}}
    ProxySettings]
   [:image
    {:description         "The container image"
     :json-schema/example {}}
    Image]])

(def NewToolContainer
  [:map
   NewSettings
   [:container_devices
    {:optional            true
     :description         "A list of devices associated with a tool's container"
     :json-schema/example []}
    [:vector NewDevice]]
   [:container_volumes
    {:optional            true
     :description         "A list of volumes associated with a tool's container"
     :json-schema/example []}
    [:vector NewVolume]]
   [:container_volumes_from
    {:optional            true
     :description         "The list of VolumeFroms associated with a tool's container"
     :json-schema/example []}
    [:vector NewVolumesFrom]]
   [:container_ports
    {:optional            true
     :description         "A list of ports associated with a tool's container"
     :json-schema/example []}
    [:vector NewPort]]
   [:interactive_apps
    {:optional            true
     :description         "Interactive app settings for the reverse proxy"
     :json-schema/example {}}
    NewProxySettings]
   [:image
    {:description         "The container image"
     :json-schema/example {}}
    NewImage]])