class Podspec < Thor::Group
  include Thor::Actions

  argument :name
  class_option :version

  def self.source_root
    File.dirname(__FILE__)
  end

  def check_version_exist
    raise StandardError, "--version is required!" unless options['version'] && !options['version'].empty?
  end

  def create_podspec_file
    template('podspec.tt', "#{name}.podspec")
  end
end