This service automatically relaunches analyses with the IDs given in the request.

* If a requested analysis was a sub-job of an HT analysis,
  then the relaunched analysis will also be a sub-job of that parent HT analysis.
* Any of the original analyses that have the `create_output_subdir` flag set to `false` will cause the corresponding
  relaunched analysis to use an output folder with `-redo-###` appended to the original output folder name,
  where `###` will be a `1` by default.
  If the original output folder name also ends with `-redo-###`,
  then the relaunched folder name will use a number 1 larger than the original.
  This will always apply to output folders of relaunched sub-jobs of an HT analysis.
  
  For example, if the output folder was `my-analysis`, then the relaunched output folder will use `my-analysis-redo-1`.
  If that relaunched analysis is in turn relaunched itself, then the next relaunched output folder will be `my-analysis-redo-2`.
* The names of relaunched HT analysis sub-jobs will also be renamed in this manner.
