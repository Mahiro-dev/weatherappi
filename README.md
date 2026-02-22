## Week 6 – Weather App with Room
What Room does?
- Room provides a local SQLite database with structured layers:
- Entity defines the table structure.
- DAO contains database queries.
- RoomDatabase creates and provides the database instance.
- Repository combines API and database logic.
- ViewModel manages UI state and calls the repository.
Compose UI observes state and updates automatically.

Project Structure

inside  data, folders model, remote, local, repository

data/

  model/
  
  remote/
  
  local/
  
  repository/
  


viewmodel/

ui/

- Remote handles Retrofit API calls.
- Local handles Room database.
- Repository connects API and Room.
- ViewModel exposes StateFlow.
- UI reacts to state changes.

## Data Flow

1. User enters a city.
2. ViewModel calls Repository.
3. Repository checks Room cache.
4. If needed, API is called via Retrofit.
5. Result is saved to Room.
6. ViewModel updates UI state.
7. Compose recomposes automatically.

Flow direction:
UI -> ViewModel -> Repository -> (Room / API) -> ViewModel -> UI

## Cache Logic

Each weather entry stores a timestamp.
When fetching data
- If cached data is less than 30 minutes old -> use Room.
- If older than 30 minutes -> fetch from API and update Room.
This reduces unnecessary network requests and allows basic offline usage

## APK 
its provided in the release
