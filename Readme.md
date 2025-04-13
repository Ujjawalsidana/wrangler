Enhancement: Byte Size and Time Duration Parsers
This enhancement adds native support for Byte Size (e.g., 10KB, 5MB) and Time Duration (e.g., 150ms, 2s) parsing in Wrangler, along with a new directive for aggregation.

🧩 New Token Types
BYTE_SIZE: Accepts values like 10KB, 1.5MB, 2GB

TIME_DURATION: Accepts values like 500ms, 1.2s, 2min

These tokens are now valid in recipes and can be used for parsing, calculations, and aggregation tasks.

🧠 New Java Classes
ByteSize.java: Parses strings like 10KB, 1MB, 1.5GB and returns byte-equivalent values via getBytes().

TimeDuration.java: Parses strings like 500ms, 2s and returns duration in nanoseconds via getNanos().

What It Does
Aggregates byte and time values across rows

Converts them to canonical units (e.g., total bytes → MB, total nanoseconds → seconds)

Outputs a single row with aggregated results

🧪 Testing
Tests were added for:

ByteSize and TimeDuration parsing

Parsing of new tokens in grammar

Full execution of aggregate-stats directive using TestingRig

All unit tests pass successfully, and edge cases (e.g., zero values, mixed case units) have been covered.

🤖 AI Tooling Used
Prompts used with AI tools like ChatGPT are recorded in AI_prompts.txt
