#!/usr/bin/env bash

# Check if the script is run from the correct directory
if [ ! -d "graph_visualizer" ]; then
    echo "Please run this script from the root directory of the project."
    exit 1
fi

VISUALIZER_DIR="graph_visualizer"
cd $VISUALIZER_DIR

# Setup venv if not
if [ ! -d "venv" ]; then
    python3 -m venv venv
    source venv/bin/activate
    pip3 install -r requirements.txt
else
    source venv/bin/activate
fi

GRAPH_DIR="../app/build/test-results/jmc-report"

# Check if the provided argument is a valid directory
if [ ! -d "$GRAPH_DIR" ]; then
    echo "Invalid directory: $1"
    exit 1
fi


# Run the visualizer
python3 web_server.py $GRAPH_DIR