# Codenjoy::Client

This is ruby client for https://github.com/codenjoyme/codenjoy project. 

## Installation

Add this line to your application's Gemfile:

```ruby
gem 'codenjoy-client'
```

And then execute:

    $ bundle install

Or install it yourself as:

    $ gem install codenjoy-client

## Usage

After you install gem, you can create base client:

    $ codenjoy-ruby-client

The file `game_base_client.rb` created at current folder. You should replace `url` and write your own code at `YourSolver` class.

## Development

After checking out the repo, run `bin/setup` to install dependencies. Then, run `rake spec` to run the tests. You can also run `bin/console` for an interactive prompt that will allow you to experiment.

To install this gem onto your local machine, run `bundle exec rake install`. To release a new version, update the version number in `version.rb`, and then run `bundle exec rake release`, which will create a git tag for the version, push git commits and tags, and push the `.gem` file to [rubygems.org](https://rubygems.org).

For add new game crate new folder at `lib/codenjoy/games` and add necessary classes.

## Contributing

Bug reports and pull requests are welcome on GitHub at https://github.com/[USERNAME]/codenjoy-client.


## License

The gem is available as open source under the terms of the [MIT License](https://opensource.org/licenses/MIT).
