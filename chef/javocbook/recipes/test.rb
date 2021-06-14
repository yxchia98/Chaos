if platform?('redhat')
        directory "#{ENV['HOME']}/redhat" do
	action :create
	end
else
        directory "#{ENV['HOME']}/notredhat" do
	action :create
	end
end

