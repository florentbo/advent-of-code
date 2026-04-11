#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")"

if [[ $# -ne 2 ]]; then
  echo "usage: $0 <year> <day>" >&2
  exit 1
fi

set -a
# shellcheck disable=SC1091
source .env
set +a

YEAR=$1 DAY=$2 mvn -q exec:java -Dexec.mainClass=be.bonamis.advent.DayDataRetriever
