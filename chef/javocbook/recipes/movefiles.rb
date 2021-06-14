if platform?('redhat')
	cookbook_file "#{ENV['HOME']}/Javoc.jar" do
	  source 'Javoc.jar'
	  mode '0755'
	  action :create
	end
	cookbook_file "#{ENV['HOME']}/openjdk-16.0.1_linux-x64_bin.tar.gz" do
	  source "openjdk-16.0.1_linux-x64_bin.tar.gz"
	  mode '0755'
	  action :create
	end
        if File.directory?("#{ENV['HOME']}/jdk-16.0.1")
	else
		execute 'unzip_openjdk(linux)' do
	  	  command <<-EOS
	  	  cd #{ENV['HOME']}
	  	  tar -xvf openjdk-16.0.1_linux-x64_bin.tar.gz
	  	  EOS
		end
	end
else
	cookbook_file "#{ENV['HOME']}/Javoc.jar" do
	  source 'Javoc.jar'
	  action :create
	end
	cookbook_file "#{ENV['HOME']}/openjdk-16.0.1_windows-x64_bin.zip" do
	  source "openjdk-16.0.1_windows-x64_bin.zip"
	  action :create
	end
	cookbook_file "#{ENV['HOME']}/clumsy-0.2-win64.zip" do
	  source 'clumsy-0.2-win64.zip'
	  action :create
	end
	if File.directory?("#{ENV['HOME']}/clumsy-0.2-win64")
        else
                powershell_script 'unzip_clumsy(windows)' do
                  code <<-EOS
                  cd $HOME
                  Expand-Archive -LiteralPath .\\clumsy-0.2-win64.zip $HOME
                  EOS
                end
        end
	if File.directory?("#{ENV['HOME']}/jdk-16.0.1")
        else
                powershell_script 'unzip_openjdk(windows)' do
                  code <<-EOS
		  cd $HOME
		  Expand-Archive -LiteralPath .\\openjdk-16.0.1_windows-x64_bin.zip $HOME
                  EOS
                end
        end

end

