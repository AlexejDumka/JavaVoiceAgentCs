# Czech Voice Agent

**CzechVoiceAgent** is an offline voice assistant written in Java that listens to your question in Czech language, sends recognized text to a local Ollama LLM model, receives a response, and speaks it back using espeak.

## Features

- Offline speech recognition using [Vosk](https://alphacephei.com/vosk/)
- LLM-based response via [Ollama](https://ollama.com/) (e.g., LLaMA3)
- Offline text-to-speech with [espeak] (https://netix.dl.sourceforge.net/project/espeak/espeak/espeak-1.48/setup_espeak-1.48.04.exe?viasf=1)
- Czech language support end-to-end

##  Tech Stack

| Component        | Tool / Library         |
|------------------|------------------------|
| Speech-to-Text   | Vosk (Java binding)    |
| LLM Backend      | Ollama + Local model   |
| Text-to-Speech   | eSpeak                 |
| JSON Processing  | org.json               |


## Requirements

- Java 11+
- Maven
- Ollama installed and running (`ollama run llama3`)
- `espeak` (installed and available in system `PATH`)
- Vosk model for Czech (e.g. [vosk-model-small-cs-0.4-rhasspy](https://alphacephei.com/vosk/models/vosk-model-small-cs-0.4-rhasspy.zip)

## Installation

### 1. Clone the repo

```
git clone https://github.com/AlexejDumka/CzechVoiceAgent.git
cd CzechVoiceAgent
