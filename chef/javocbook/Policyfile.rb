# Policyfile.rb - Describe how you want Chef Infra Client to build your system.
#
# For more information on the Policyfile feature, visit
# https://docs.chef.io/policyfile/

# A name that describes what the system you're building with Chef does.
name 'javocbook'

# Where to find external cookbooks:
default_source :chef_repo, '/home/user/chef-repo/cookbooks'

# run_list: chef-client will run these recipes in the order specified.
run_list 'javocbook::default'

# Specify a custom source for a single cookbook:
cookbook 'javocbook', path: '.'
