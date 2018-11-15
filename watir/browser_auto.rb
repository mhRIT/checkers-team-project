require 'watir'

browser0 = Watir::Browser.new :chrome
browser0.goto '127.0.0.1:4567'

browser1 = Watir::Browser.new :chrome
browser1.goto '127.0.0.2:4567'

# browser.text_field(title: 'Search').set 'Hello World!'
# browser.button(type: 'submit').click

# puts browser.title
# => 'Hello World! - Google Search'
# browser.close
