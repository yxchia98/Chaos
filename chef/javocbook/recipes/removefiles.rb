if platform?('redhat')

	file "#{ENV['HOME']}/Javoc.jar" do
		only_if { ::File.exist?("#{ENV['HOME']}/Javoc.jar") }
		action :delete
	end

	file "#{ENV['HOME']}/openjdk-16.0.1_linux-x64_bin.tar.gz" do
		only_if { ::File.exist?("#{ENV['HOME']}/openjdk-16.0.1_linux-x64_bin.tar.gz") }
		action :delete
	end
	
	directory "#{ENV['HOME']}/jdk-16.0.1" do
		recursive true
		action :delete
	end

else
	file "#{ENV['HOME']}/Javoc.jar" do
		only_if { ::File.exist?("#{ENV['HOME']}/Javoc.jar") }
		action :delete
	end

	directory "#{ENV['HOME']}/jdk-16.0.1" do
		recursive true
		action :delete
	end

	file "#{ENV['HOME']}/openjdk-16.0.1_windows-x64_bin.zip" do
		only_if { ::File.exist?("#{ENV['HOME']}/openjdk-16.0.1_windows-x64_bin.zip") }
		action :delete
	end

	file "#{ENV['HOME']}/clumsy-0.2-win64.zip" do
		only_if { ::File.exist?("#{ENV['HOME']}/clumsy-0.2-win64.zip") }
		action :delete
	end

	directory "#{ENV['HOME']}/clumsy-0.2-win64" do
		recursive true
		action :delete
	end
end

