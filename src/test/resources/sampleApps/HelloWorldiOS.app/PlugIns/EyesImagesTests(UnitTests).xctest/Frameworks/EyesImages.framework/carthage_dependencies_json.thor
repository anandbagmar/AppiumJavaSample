class Dependencies_Json < Thor::Group
  include Thor::Actions

  argument :name
  class_option :version

  def self.source_root
    File.dirname(__FILE__)
  end

  def check_version_exist
    raise StandardError, "--version is required!" unless options['version'] && !options['version'].empty?
  end

  def create_json_file
    template('carthage_dependencies_json', "#{name}-#{options['version']}.json")
  end
end
