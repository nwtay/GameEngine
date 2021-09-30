import com.alexmerz.graphviz.*;
import com.alexmerz.graphviz.objects.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class EntityParser {

    private final String entityFilename;
    private Parser parser;
    private ArrayList<Location> locationList;
    private HashMap<String, Entity> entityLog;

    public EntityParser(String entityFilename)
    {
        this.entityFilename = entityFilename;
    }

    public void parseAndLoad()
    {
        try {
            parser = new Parser();
            FileReader reader = new FileReader(entityFilename);
            parser.parse(reader);
            locationList = new ArrayList<>();
            entityLog = new HashMap<>();
            loadEntities();

        } catch (FileNotFoundException | ParseException exception ) {
            System.out.println("Parsing of entity file failed: " + exception);
        }
    }

    // loads all entities into the game in a tree like fashion (all entities stored in locations)
    private void loadEntities() throws FileNotFoundException, com.alexmerz.graphviz.ParseException
    {
        // gets all the digraphs in the entities file
        ArrayList<Graph> diGraphs = parser.getGraphs();
        // gets the first subgraph from digraph - i.e., the locations subgraph
        ArrayList<Graph> locationsSubGraph = diGraphs.get(0).getSubgraphs();

        // looping through each subgraph in locationsSubGraph (i.e., each cluster)
        for(Graph clusterGraph : locationsSubGraph){

            // making a list of subGraphs for each cluster
            ArrayList<Graph> subGraphs1 = clusterGraph.getSubgraphs();

            // looping through each cluster's sub graph in the list of sub graphs
            for (Graph subGraph : subGraphs1){

                // for each subGraph, get the node
                ArrayList<Node> nodesList = subGraph.getNodes(false);
                // the node is the first element in the node list
                Node node = nodesList.get(0);
                // create a location using the name and description of this first node
                Location newLocation = new Location(node.getId().getId(), node.getAttribute("description"));
                // each node (location) has a list of subGraphs for entities inside a location
                ArrayList<Graph> entitiesSubGraph = subGraph.getSubgraphs();
                addLocationEntities(entitiesSubGraph, newLocation);
                // add the new location defined in outer subGraph to the list of locations
                locationList.add(newLocation);

            }
            // adds all the paths (to other locations)
            addEdges(clusterGraph);
        }
    }

    // adds the locations
    private void addLocationEntities(ArrayList<Graph> entitiesSubGraph, Location newLocation)
    {
        // looping through each entity graph in list of entity graphs
        for (Graph entityGraph : entitiesSubGraph) {

            // each element in the list is a node, which represents an entity within that location subGraph
            ArrayList<Node> entityNode = entityGraph.getNodes(false);

            // looping through each node within that entity node (in case we have embedded nodes within entities)
            for (Node entity : entityNode) {

                // for each node within the entity node, we create an entity using the node's id and description
                Entity newEntity = createEntity(entityGraph.getId().getId(), entity.getId().getId(), entity.getAttribute("description"));

                // and we add this entity to the location defined by the outer subGraph
                newLocation.addEntity(newEntity);
            }
        }
    }

    // adds the edges of the graph
    private void addEdges(Graph graph)
    {
        // gets all edges from the incoming graph (the list of paths)
        ArrayList<Edge> edges = graph.getEdges();
        for (Edge edge : edges){
            // for each edge, get the location object that corresponds to the source, and same for target
            Location fromLocation = findLocation(edge.getSource().getNode().getId().getId());
            Location toLocation = findLocation(edge.getTarget().getNode().getId().getId());
            if(fromLocation != null) {
                // add the target location to the source location
                fromLocation.addPath(toLocation);
            }
        }
    }

    public ArrayList<Location> getLocationList()
    {
        return locationList;
    }

    // finds a location by it's name if it exists
    private Location findLocation(String locationName)
    {
        for (Location location : locationList) {
            if (location.getName().equals(locationName)) {
                return location;
            }
        }
        return null;
    }

    private Entity createEntity(String entityType, String entityName, String entityDescription)
    {
        entityLog.put("artefacts", new Artefact(entityName, entityDescription));
        entityLog.put("furniture", new Furniture(entityName, entityDescription));
        entityLog.put("characters", new Character(entityName, entityDescription));
        return entityLog.get(entityType);
    }


}
