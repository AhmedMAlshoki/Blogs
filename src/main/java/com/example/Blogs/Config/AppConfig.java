package com.example.Blogs.Config;



import com.example.Blogs.DAOs.CommentDAO;
import com.example.Blogs.DAOs.PostDAO;
import com.example.Blogs.DAOs.UserDAO;
import com.example.Blogs.Enums.Timezone;
import com.example.Blogs.Models.Comment;
import com.example.Blogs.Models.Post;
import com.example.Blogs.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
public class AppConfig implements CommandLineRunner {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Random random = new Random();
    private List<User> allUsers = new ArrayList<>();
    private List<Post> allPosts = new ArrayList<>();
    private Timezone[] allTimezones = Timezone.values();

    @Override
    public void run(String... args) throws Exception {
        insertSampleData();
    }

    private void insertSampleData() {
        try {
            System.out.println("Inserting massive sample data...");
            //createUsers();
            //createPosts();
            createComments();
            createLikes();
            createRelationships();

            System.out.println("Massive sample data inserted successfully!");
            System.out.println("Created: " + allUsers.size() + " users, " + allPosts.size() + " posts");

        } catch (Exception e) {
            System.err.println("Error inserting sample data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createUsers() {
        System.out.println("Creating  Users...");

        // Mixed regions - shuffled intentionally
        allUsers.add(createUser("john_doe", "John Doe", "john.doe@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("maria_garcia", "María García", "maria.garcia@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("alex_wong", "Alex Wong", "alex.wong@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("sophie_martin", "Sophie Martin", "sophie.martin@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("wei_li", "Wei Li", "wei.li@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("ahmed_hassan", "Ahmed Hassan", "ahmed.hassan@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("jane_smith", "Jane Smith", "jane.smith@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("carlos_rodriguez", "Carlos Rodríguez", "carlos.rodriguez@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("kenji_tanaka", "Kenji Tanaka", "kenji.tanaka@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("olga_ivanova", "Olga Ivanova", "olga.ivanova@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("priya_patel", "Priya Patel", "priya.patel@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("luca_rossi", "Luca Rossi", "luca.rossi@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("fatima_al", "Fatima Al-Mansoori", "fatima.al@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("mike_johnson", "Mike Johnson", "mike.johnson@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("ana_martinez", "Ana Martínez", "ana.martinez@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("yuki_yamamoto", "Yuki Yamamoto", "yuki.yamamoto@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("james_wilson", "James Wilson", "james.wilson@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("zara_mohammed", "Zara Mohammed", "zara.mohammed@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("emily_wilson", "Emily Wilson", "emily.wilson@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("raj_kumar", "Raj Kumar", "raj.kumar@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("sarah_chen", "Sarah Chen", "sarah.chen@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("pablo_gonzalez", "Pablo González", "pablo.gonzalez@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("min_lee", "Min Lee", "min.lee@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("anna_schmidt", "Anna Schmidt", "anna.schmidt@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("omar_ibrahim", "Omar Ibrahim", "omar.ibrahim@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("liam_walker", "Liam Walker", "liam.walker@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("eva_johansson", "Eva Johansson", "eva.johansson@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("jose_hernandez", "José Hernández", "jose.hernandez@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("sophia_taylor", "Sophia Taylor", "sophia.taylor@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("chris_brown", "Chris Brown", "chris.brown@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("ling_wei", "Ling Wei", "ling.wei@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("daniel_kim", "Daniel Kim", "daniel.kim@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("isabella_rossi", "Isabella Rossi", "isabella.rossi@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("mohammed_ali", "Mohammed Ali", "mohammed.ali@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("elena_popescu", "Elena Popescu", "elena.popescu@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("hassan_rahman", "Hassan Rahman", "hassan.rahman@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("sasha_ivanov", "Sasha Ivanov", "sasha.ivanov@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("lila_patel", "Lila Patel", "lila.patel@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("carlos_silva", "Carlos Silva", "carlos.silva@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("yasmine_el", "Yasmine El-Masry", "yasmine.el@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("vikram_singh", "Vikram Singh", "vikram.singh@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("santiago_reyes", "Santiago Reyes", "santiago.reyes@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("anika_sharma", "Anika Sharma", "anika.sharma@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("leonardo_conti", "Leonardo Conti", "leonardo.conti@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("amina_jones", "Amina Jones", "amina.jones@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("dmitry_volkov", "Dmitry Volkov", "dmitry.volkov@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("lucia_fernandez", "Lucía Fernández", "lucia.fernandez@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("tariq_ahmed", "Tariq Ahmed", "tariq.ahmed@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("chiara_marino", "Chiara Marino", "chiara.marino@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("samuel_owusu", "Samuel Owusu", "samuel.owusu@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("elena_kuznetsova", "Elena Kuznetsova", "elena.kuznetsova@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("ricardo_lima", "Ricardo Lima", "ricardo.lima@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("sakura_takahashi", "Sakura Takahashi", "sakura.takahashi@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("marcus_black", "Marcus Black", "marcus.black@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("fatoumata_diallo", "Fatoumata Diallo", "fatoumata.diallo@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("antonio_costa", "Antonio Costa", "antonio.costa@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("mei_lin", "Mei Lin", "mei.lin@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("kofi_annor", "Kofi Annor", "kofi.annor@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("elena_vargas", "Elena Vargas", "elena.vargas@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("hannah_muller", "Hannah Müller", "hannah.muller@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("maya_sharma", "Maya Sharma", "maya.sharma@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("leo_chen", "Leo Chen", "leo.chen@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("isabelle_roy", "Isabelle Roy", "isabelle.roy@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("omar_khan", "Omar Khan", "omar.khan@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("sofia_lima", "Sofia Lima", "sofia.lima@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("benjamin_ng", "Benjamin Ng", "benjamin.ng@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("zoe_park", "Zoe Park", "zoe.park@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("alexei_ivanov", "Alexei Ivanov", "alexei.ivanov@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("nadia_ali", "Nadia Ali", "nadia.ali@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("liam_oconnor", "Liam O'Connor", "liam.oconnor@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("chloe_moreau", "Chloe Moreau", "chloe.moreau@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("ryan_mitchell", "Ryan Mitchell", "ryan.mitchell@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("anya_sharma", "Anya Sharma", "anya.sharma@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("marco_ferrari", "Marco Ferrari", "marco.ferrari@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("lily_wang", "Lily Wang", "lily.wang@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("samir_ahmed", "Samir Ahmed", "samir.ahmed@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("elena_silva", "Elena Silva", "elena.silva@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("noah_kim", "Noah Kim", "noah.kim@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("mia_rodriguez", "Mia Rodriguez", "mia.rodriguez@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("kaito_tanaka", "Kaito Tanaka", "kaito.tanaka@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("sanjay_patel", "Sanjay Patel", "sanjay.patel@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("emma_green", "Emma Green", "emma.green@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("david_cohen", "David Cohen", "david.cohen@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("oliver_wright", "Oliver Wright", "oliver.wright@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("yasmin_el", "Yasmin El-Amin", "yasmin.el@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("jack_thompson", "Jack Thompson", "jack.thompson@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("isla_murphy", "Isla Murphy", "isla.murphy@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("mohammad_khan", "Mohammad Khan", "mohammad.khan@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("ava_scott", "Ava Scott", "ava.scott@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("ethan_lee", "Ethan Lee", "ethan.lee@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("grace_taylor", "Grace Taylor", "grace.taylor@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("william_brown", "William Brown", "william.brown@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("zara_khan", "Zara Khan", "zara.khan@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("lucas_martin", "Lucas Martin", "lucas.martin@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("harper_jones", "Harper Jones", "harper.jones@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("daniel_garcia", "Daniel Garcia", "daniel.garcia@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("charlotte_davis", "Charlotte Davis", "charlotte.davis@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("mason_miller", "Mason Miller", "mason.miller@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("amelia_wilson", "Amelia Wilson", "amelia.wilson@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("logan_moore", "Logan Moore", "logan.moore@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("evelyn_white", "Evelyn White", "evelyn.white@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("jacob_anderson", "Jacob Anderson", "jacob.anderson@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("abigail_thomas", "Abigail Thomas", "abigail.thomas@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("michael_jackson", "Michael Jackson", "michael.jackson@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("ella_harris", "Ella Harris", "ella.harris@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("alexander_clark", "Alexander Clark", "alexander.clark@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("scarlett_lewis", "Scarlett Lewis", "scarlett.lewis@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("sebastian_robinson", "Sebastian Robinson", "sebastian.robinson@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("victoria_walker", "Victoria Walker", "victoria.walker@email.com", "password123", getRandomTimezone()));
        allUsers.add(createUser("matthew_hall", "Matthew Hall", "matthew.hall@email.com", "password123", getRandomTimezone()));
    }

    private void createPosts() {
        System.out.println("Creating  Posts...");

        allPosts.add(createPost(23L, "The Impact of 5G on Mobile Technology",
                "5G technology represents the fifth generation of cellular network technology, offering significantly faster data speeds, lower latency, and greater capacity than previous generations. With theoretical download speeds reaching up to 10 Gbps, 5G enables seamless streaming of 4K and 8K video, real-time cloud gaming, and instant downloads of large files. The reduced latency of just 1-10 milliseconds makes applications like autonomous vehicles, remote surgery, and industrial automation possible. Beyond consumer applications, 5G is transforming industries through smart factories, connected healthcare devices, and smart city infrastructure. However, challenges remain in terms of infrastructure deployment, spectrum allocation, and ensuring consistent coverage. The rollout of 5G-Advanced and future 6G technologies promises even more revolutionary changes in how we connect and communicate.",
                getRandomTimezone()));

        allPosts.add(createPost(67L, "Sustainable Agriculture Practices",
                "Modern agriculture faces the dual challenge of feeding a growing global population while minimizing environmental impact. Sustainable farming practices include crop rotation, which maintains soil health and reduces pest problems; cover cropping to prevent soil erosion; and integrated pest management that reduces chemical pesticide use. Precision agriculture uses GPS, sensors, and data analytics to optimize water, fertilizer, and pesticide application, reducing waste and environmental runoff. Agroforestry combines trees with crops or livestock to create more diverse, productive, and sustainable land-use systems. Organic farming eliminates synthetic inputs while promoting soil biodiversity. These methods not only protect the environment but often improve farm profitability through reduced input costs and premium markets for sustainably produced foods.",
                getRandomTimezone()));

        allPosts.add(createPost(45L, "The Psychology of Color in Marketing",
                "Color psychology plays a crucial role in consumer behavior and brand perception. Different colors evoke specific emotional responses: blue conveys trust and security, making it popular for financial institutions; red creates urgency and excitement, often used in clearance sales; green represents nature and health, common in organic and eco-friendly products; yellow attracts attention and conveys optimism. Cultural differences significantly impact color meanings—while white symbolizes purity in Western cultures, it represents mourning in some Eastern cultures. Successful brands carefully choose their color palettes to align with their values and target audience. Research shows that color can increase brand recognition by up to 80% and proper color matching can improve readership by 40%. Understanding these psychological triggers helps marketers create more effective campaigns and build stronger brand identities.",
                getRandomTimezone()));

        allPosts.add(createPost(12L, "Advancements in Renewable Energy Storage",
                "The intermittent nature of renewable energy sources like solar and wind has driven innovation in energy storage technologies. Lithium-ion batteries continue to improve in energy density and cost-effectiveness, with prices falling nearly 90% over the past decade. Flow batteries offer longer duration storage suitable for grid-scale applications, while solid-state batteries promise higher safety and energy density. Pumped hydro storage remains the largest-capacity storage method globally, but new gravitational storage systems using weights in disused mines show promise. Green hydrogen produced from renewable electricity offers seasonal storage capabilities. Thermal storage systems capture heat in materials like molten salt for later electricity generation. These advancements are crucial for achieving high renewable energy penetration, with some systems now providing levelized storage costs below $100 per MWh.",
                getRandomTimezone()));

        allPosts.add(createPost(89L, "The Future of Remote Work Technology",
                "Remote work technology has evolved from basic video conferencing to comprehensive digital workplace platforms. Advanced collaboration tools now include virtual whiteboards, asynchronous video messaging, and AI-powered meeting assistants that generate transcripts and action items. Virtual reality meeting spaces create immersive environments where remote teams can interact as if they were physically together. Digital twin technology allows remote monitoring and control of physical operations. Cybersecurity has become paramount with distributed workforces, driving adoption of zero-trust architectures and secure access service edge (SASE) solutions. AI-driven productivity tools help managers track team wellbeing and prevent burnout without invasive monitoring. The future points toward hybrid reality workplaces that seamlessly blend physical and digital interactions, with haptic feedback technology enabling remote tactile experiences.",
                getRandomTimezone()));

        allPosts.add(createPost(23L, "Neuroscience and Learning Enhancement",
                "Recent neuroscience discoveries are revolutionizing our understanding of learning and memory formation. Neuroplasticity research shows that the brain remains malleable throughout life, capable of forming new neural connections in response to learning. Techniques like spaced repetition leverage the spacing effect to improve long-term retention, while interleaving different subjects enhances pattern recognition and problem-solving skills. Neurotransmitters like dopamine play crucial roles in motivation and reward-based learning. Brain stimulation methods such as transcranial direct current stimulation (tDCS) show promise in accelerating skill acquisition. Understanding sleep's role in memory consolidation has led to optimized learning schedules that align with circadian rhythms. These insights are driving personalized learning approaches that adapt to individual neurological profiles, potentially making education more effective and accessible for diverse learners.",
                getRandomTimezone()));

        allPosts.add(createPost(56L, "Blockchain in Supply Chain Management",
                "Blockchain technology brings unprecedented transparency and efficiency to supply chain management. By creating immutable, distributed records of transactions, blockchain enables real-time tracking of goods from raw material sourcing to final delivery. Smart contracts automate processes like payments and compliance verification when predetermined conditions are met. In food supply chains, blockchain can reduce trace-back time for contaminated products from days to seconds, potentially saving lives and reducing waste. Luxury goods manufacturers use blockchain to combat counterfeiting by providing verifiable product histories. The technology also improves ethical sourcing by creating auditable records of labor practices and environmental impact. Major companies like Walmart and Maersk have implemented blockchain solutions that reduced paperwork by 80% and improved shipment visibility. Challenges remain in standardization and interoperability between different blockchain platforms.",
                getRandomTimezone()));

        allPosts.add(createPost(34L, "Climate Change and Coastal Cities",
                "Coastal cities face existential threats from climate change, including sea-level rise, increased storm intensity, and coastal erosion. Current projections indicate global sea levels could rise 0.5 to 2 meters by 2100, threatening cities like Miami, Shanghai, and Mumbai. Adaptation strategies include constructing sea walls and flood barriers, elevating critical infrastructure, and implementing managed retreat from vulnerable areas. Nature-based solutions like mangrove restoration and oyster reef construction provide cost-effective coastal protection while enhancing biodiversity. Amsterdam's water management expertise and Tokyo's extensive flood control systems offer valuable models. Urban planning must integrate climate resilience through elevated building designs, water-absorbent landscapes, and redundant systems. The economic costs are staggering—without adaptation, annual flood damage in coastal cities could exceed $1 trillion by 2050. Successful adaptation requires coordinated action across government, business, and community levels.",
                getRandomTimezone()));

        allPosts.add(createPost(78L, "The Evolution of E-commerce",
                "E-commerce has evolved from simple online catalogs to sophisticated personalized shopping experiences powered by artificial intelligence. The first generation focused on basic transactions, while current platforms use machine learning to recommend products, optimize pricing, and predict inventory needs. Social commerce integrates shopping directly into social media platforms, with live streaming sales becoming particularly popular in Asian markets. Augmented reality allows customers to visualize products in their homes before purchasing. Voice commerce through smart speakers is growing rapidly, with voice shopping expected to reach $40 billion by 2024. Subscription models and personalized curation services create ongoing customer relationships. The pandemic accelerated adoption of same-day delivery and buy-online-pickup-in-store options. Future trends include hyper-personalization using biometric data and the integration of virtual reality shopping experiences that replicate physical store browsing.",
                getRandomTimezone()));

        allPosts.add(createPost(12L, "Mental Health in the Digital Age",
                "The digital revolution has created both challenges and solutions for mental health. Constant connectivity can contribute to anxiety, sleep disorders, and attention fragmentation, with the average person now checking their phone 58 times daily. Social media comparison culture has been linked to increased depression rates, particularly among adolescents. However, digital technology also provides unprecedented access to mental health resources. Teletherapy platforms make professional help accessible to remote populations, while mental health apps offer meditation guidance, mood tracking, and crisis support. AI-powered chatbots provide immediate counseling, and virtual reality exposure therapy helps treat phobias and PTSD. The key is developing digital literacy and healthy boundaries with technology, including digital detox practices and mindful usage. Employers are increasingly recognizing their role in supporting employee mental health through flexible work arrangements and mental health benefits.",
                getRandomTimezone()));
        allPosts.add(createPost(91L, "Artificial Intelligence in Creative Arts",
                "The integration of artificial intelligence in creative fields is revolutionizing artistic expression and challenging traditional notions of creativity. AI algorithms can now generate original paintings in the style of famous artists, compose music that rivals human compositions, and write poetry and stories that evoke genuine emotional responses. Tools like DALL-E, Midjourney, and Stable Diffusion enable artists to create stunning visual art from text descriptions, while AI music composition systems can generate complete orchestral scores. In literature, language models like GPT-4 assist writers with brainstorming, editing, and even generating entire chapters. However, these advancements raise important questions about copyright, artistic ownership, and the very definition of art. Many artists are embracing AI as a collaborative tool rather than a replacement, using it to enhance their creative process and explore new artistic territories. The intersection of AI and creativity represents one of the most exciting and controversial frontiers in contemporary art.",
                getRandomTimezone()));

        allPosts.add(createPost(45L, "Urban Planning for Future Cities",
                "Modern urban planning is evolving to address the complex challenges of population growth, climate change, and technological advancement. The concept of the 15-minute city, where residents can access most daily needs within a 15-minute walk or bike ride, is gaining traction as a model for sustainable urban living. Smart city technologies integrate IoT sensors, data analytics, and automation to optimize traffic flow, reduce energy consumption, and improve public services. Green infrastructure, including vertical gardens, green roofs, and urban forests, helps mitigate the urban heat island effect and improve air quality. Mixed-use developments combine residential, commercial, and recreational spaces to create vibrant, walkable neighborhoods. Transportation planning increasingly prioritizes pedestrians, cyclists, and public transit over private vehicles. These approaches aim to create cities that are not only more efficient and sustainable but also more livable, equitable, and resilient to future challenges.",
                getRandomTimezone()));

        allPosts.add(createPost(23L, "Cybersecurity in IoT Devices",
                "The rapid proliferation of Internet of Things devices has created unprecedented cybersecurity challenges. From smart home assistants and security cameras to industrial sensors and medical devices, billions of connected devices represent potential entry points for cyberattacks. Many IoT devices suffer from weak default passwords, unencrypted communications, and lack of regular security updates. The Mirai botnet attack demonstrated how vulnerable IoT devices can be weaponized for large-scale distributed denial-of-service attacks. Securing the IoT ecosystem requires a multi-layered approach including device authentication, network segmentation, regular firmware updates, and end-to-end encryption. Manufacturers are increasingly implementing security-by-design principles, while regulatory frameworks like the EU's Cyber Resilience Act aim to establish minimum security standards. Consumers can protect themselves by changing default credentials, disabling unnecessary features, and keeping devices updated. As IoT continues to expand into critical infrastructure and healthcare, robust security measures become increasingly essential for public safety.",
                getRandomTimezone()));

        allPosts.add(createPost(67L, "The Science of Habit Formation",
                "Understanding the neuroscience and psychology of habit formation can help individuals create positive behaviors and break negative ones. Habits form through a neurological loop consisting of a cue, routine, and reward. The basal ganglia, a deep brain structure, plays a key role in storing and executing habitual behaviors, allowing the prefrontal cortex to focus on more complex tasks. Research shows that it typically takes 18 to 254 days for a new behavior to become automatic, with 66 days being the average. Effective habit formation strategies include starting with small, achievable changes, leveraging existing routines as triggers, and creating immediate rewards. Implementation intentions—specific plans that detail when, where, and how a behavior will be performed—significantly increase success rates. Environmental design, such as keeping healthy foods visible and accessible, can support habit formation. Understanding these principles empowers people to deliberately shape their behaviors and ultimately their lives.",
                getRandomTimezone()));

        allPosts.add(createPost(34L, "Renewable Energy Microgrids",
                "Microgrids are localized energy systems that can operate independently from the main electrical grid, providing resilience, reliability, and access to renewable energy. These systems typically combine solar panels, wind turbines, and battery storage with sophisticated control systems that manage energy production and consumption. Community microgrids enable neighborhoods to share renewable energy resources, while campus microgrids power universities, hospitals, and military bases with greater reliability. During natural disasters or grid outages, microgrids can continue providing power to critical facilities. Advanced microgrids use artificial intelligence to optimize energy dispatch, predict generation from renewable sources, and manage demand response. The declining cost of solar panels and batteries has made microgrids increasingly economically viable. In developing regions, microgrids provide electricity to remote communities that lack grid access. As climate change increases the frequency of extreme weather events, microgrids represent a crucial strategy for building more resilient and sustainable energy infrastructure.",
                getRandomTimezone()));

        allPosts.add(createPost(78L, "Digital Detox Strategies",
                "In an increasingly connected world, digital detox—intentionally reducing technology use—has become essential for mental health and wellbeing. The average person spends over 6 hours daily with digital media, leading to digital fatigue, attention fragmentation, and increased stress. Effective digital detox strategies include designated tech-free times (such as during meals or the first hour after waking), tech-free zones (especially bedrooms), and regular digital sabbaths lasting 24 hours or more. App blockers and screen time monitors help enforce boundaries, while analog alternatives like physical books and notebooks provide technology-free entertainment and productivity. Mindfulness practices help cultivate awareness of technology use patterns and their emotional impacts. Many people find that reducing social media consumption and turning off non-essential notifications significantly improves their quality of life. The goal isn't complete technology avoidance but rather developing a more intentional, balanced relationship with digital tools that serves rather than controls our lives.",
                getRandomTimezone()));

        allPosts.add(createPost(56L, "Quantum Computing Applications",
                "Quantum computing harnesses the principles of quantum mechanics to perform computations that are infeasible for classical computers. While still in early stages, quantum computers show promise for solving complex optimization problems, simulating quantum systems, and breaking current cryptographic protocols. In drug discovery, quantum computers can model molecular interactions at an atomic level, potentially accelerating the development of new medications. Financial institutions are exploring quantum algorithms for portfolio optimization and risk analysis. Logistics companies could use quantum computing to optimize global supply chains and routing. Quantum machine learning may unlock new patterns in large datasets. However, significant challenges remain including quantum decoherence, error rates, and the need for extreme cooling. Current quantum computers with 50-100 qubits represent the Noisy Intermediate-Scale Quantum era, with fault-tolerant quantum computing still years away. The race for quantum advantage—solving a practical problem faster than classical computers—continues to drive research and investment in this transformative technology.",
                getRandomTimezone()));

        allPosts.add(createPost(91L, "The Future of Transportation",
                "Transportation is undergoing its most significant transformation since the invention of the automobile. Electric vehicles are approaching price parity with internal combustion engines, with global EV sales growing exponentially. Autonomous vehicle technology continues to advance, with some cities already hosting robotaxi services. Hyperloop concepts promise to revolutionize long-distance travel with near-supersonic speeds in low-pressure tubes. Urban air mobility, including electric vertical takeoff and landing aircraft, could alleviate ground congestion. Mobility-as-a-service platforms integrate various transportation options into seamless, on-demand services. These changes are driven by converging trends in electrification, automation, connectivity, and sharing economies. The environmental benefits are substantial—transportation accounts for about one-fifth of global CO2 emissions. However, challenges include infrastructure requirements, regulatory frameworks, and social acceptance. The future transportation ecosystem will likely be characterized by increased efficiency, reduced environmental impact, and fundamentally different relationships between people and mobility.",
                getRandomTimezone()));

        allPosts.add(createPost(12L, "Biodegradable Materials Innovation",
                "The development of advanced biodegradable materials offers promising solutions to the global plastic pollution crisis. Researchers are creating bioplastics from renewable sources like corn starch, seaweed, and agricultural waste that break down completely in industrial composting facilities. Mushroom-based packaging materials grown from mycelium provide protective cushioning that decomposes in weeks rather than centuries. Alginate-based materials derived from seaweed can replace single-use plastics for items like water bottles and food containers. Some innovative materials even incorporate nutrients that support plant growth as they decompose. The challenge remains scaling production while maintaining performance characteristics and ensuring proper disposal infrastructure. While biodegradable materials represent important progress, reducing overall consumption and improving recycling systems remain essential components of comprehensive waste management. As consumer demand for sustainable products grows and regulations targeting single-use plastics expand, biodegradable materials are poised to play an increasingly important role in creating a circular economy.",
                getRandomTimezone()));

        allPosts.add(createPost(45L, "Virtual Reality in Therapy",
                "Virtual reality is emerging as a powerful tool in mental health treatment, offering controlled, immersive environments for therapeutic interventions. Exposure therapy using VR allows patients to confront fears and traumatic memories in gradual, manageable steps while remaining in the safety of a therapist's office. VR has shown particular effectiveness in treating post-traumatic stress disorder, phobias, and anxiety disorders. For pain management, immersive VR experiences can distract patients from discomfort, reducing the need for medication. Social skills training in virtual environments helps individuals with autism spectrum disorder practice interactions in low-stakes settings. Mindfulness and meditation apps using VR create tranquil environments that enhance relaxation practices. The technology also enables remote therapy sessions where therapist and patient share the same virtual space. While cost and accessibility remain barriers, the growing evidence base for VR therapy's effectiveness is driving increased adoption in clinical settings. As the technology becomes more affordable and sophisticated, VR promises to expand access to effective mental health treatments.",
                getRandomTimezone()));
        allPosts.add(createPost(67L, "The Economics of Streaming Services",
                "The streaming industry has transformed entertainment consumption but faces complex economic challenges. With over 200 streaming services available, market fragmentation has led to subscription fatigue among consumers. The initial growth-at-all-costs model has shifted toward profitability, resulting in price increases, password-sharing crackdowns, and ad-supported tiers. Content acquisition costs have skyrocketed, with some studios spending over $20 billion annually on original programming. The industry is now consolidating through mergers and acquisitions, as seen with Discovery-WarnerMedia and Amazon-MGM. Data analytics play a crucial role in content decisions, with algorithms determining which shows get renewed and what new content gets greenlit. International expansion presents both opportunity and challenge, requiring localization and navigating diverse regulatory environments. The future likely holds bundled offerings, more live sports integration, and continued experimentation with release strategies. As the market matures, the focus is shifting from subscriber growth to sustainable business models and profitability.",
                getRandomTimezone()));

        allPosts.add(createPost(23L, "Precision Medicine Advances",
                "Precision medicine represents a fundamental shift from one-size-fits-all healthcare to treatments tailored to individual genetic profiles, lifestyles, and environments. Genomic sequencing costs have plummeted from $100 million to under $1000, making comprehensive genetic analysis accessible. CRISPR gene editing technology enables precise correction of genetic defects, with clinical trials showing promise for sickle cell anemia and beta-thalassemia. Liquid biopsies can detect cancer DNA in blood samples years before symptoms appear, enabling early intervention. Pharmacogenomics helps predict individual responses to medications, reducing adverse drug reactions that hospitalize 2 million Americans annually. AI algorithms analyze medical images with greater accuracy than human radiologists for certain conditions. The integration of wearable device data provides real-time health monitoring and early warning systems. While challenges remain in data privacy, regulatory approval, and equitable access, precision medicine promises to extend healthy lifespans and transform chronic disease management through truly personalized care.",
                getRandomTimezone()));

        allPosts.add(createPost(34L, "Sustainable Fashion Revolution",
                "The fashion industry, responsible for 10% of global carbon emissions, is undergoing a sustainability transformation. Circular fashion models prioritize clothing reuse, repair, and recycling over disposable fast fashion. Innovative materials include fabrics made from agricultural waste, mushroom leather, and fibers derived from recycled ocean plastic. Waterless dyeing technologies reduce the 20,000 liters typically needed to produce 1kg of cotton. Blockchain technology enables supply chain transparency, allowing consumers to verify ethical production practices. Rental and subscription services extend garment lifespans, with the secondhand market projected to double by 2027. Brands are adopting regenerative agriculture practices for natural fibers and implementing take-back programs for end-of-life recycling. Consumer awareness is driving change, with 75% of millennials considering sustainability in purchasing decisions. While greenwashing remains a concern, third-party certifications and stricter regulations are helping distinguish genuine commitments from marketing claims in the journey toward a truly sustainable fashion industry.",
                getRandomTimezone()));

        allPosts.add(createPost(78L, "Edge Computing Infrastructure",
                "Edge computing brings computation and data storage closer to where it's needed, reducing latency and bandwidth usage. Unlike traditional cloud computing that processes data in centralized data centers, edge computing distributes processing across networks of localized microdata centers. This architecture is essential for applications requiring real-time responses, such as autonomous vehicles that must make split-second decisions and industrial IoT systems monitoring manufacturing equipment. 5G networks enable edge computing by providing high-speed, low-latency connectivity. Retailers use edge computing for inventory management and personalized customer experiences, while healthcare applications include remote patient monitoring and real-time medical imaging analysis. Security considerations differ from cloud models, requiring protection across distributed nodes rather than centralized facilities. The global edge computing market is projected to reach $250 billion by 2024, driven by growth in IoT devices, AI applications, and the need for real-time processing across numerous industries.",
                getRandomTimezone()));

        allPosts.add(createPost(56L, "Ocean Energy Harvesting",
                "Ocean energy represents a vast, untapped renewable resource with the potential to provide predictable, consistent power. Wave energy converters capture the kinetic energy of surface waves, while tidal turbines harness the reliable energy from rising and falling tides. Ocean thermal energy conversion (OTEC) uses temperature differences between warm surface water and cold deep water to generate electricity. Salinity gradient power exploits the difference in salt concentration between seawater and freshwater. The theoretical global potential of ocean energy exceeds current worldwide electricity demand, though practical limitations reduce this substantially. Pilot projects in Scotland, Canada, and Australia are demonstrating commercial viability, with some tidal arrays already supplying power to grids. Challenges include harsh marine environments, maintenance difficulties, and environmental impacts on marine ecosystems. However, the predictability of tidal energy offers advantages over intermittent solar and wind power. As technology improves and costs decrease, ocean energy could become a significant contributor to the global renewable energy mix.",
                getRandomTimezone()));

        allPosts.add(createPost(91L, "The Science of Sleep Optimization",
                "Sleep science has revealed that quality rest is fundamental to physical health, cognitive function, and emotional wellbeing. The sleep cycle consists of four stages repeating every 90-120 minutes, with deep sleep crucial for physical restoration and REM sleep essential for memory consolidation and emotional processing. Circadian rhythms regulated by the suprachiasmatic nucleus align sleep-wake cycles with daylight, explaining why blue light from screens disrupts natural sleep patterns. Optimal sleep temperature is around 65°F (18°C), as the body needs to drop its core temperature to initiate sleep. Sleep tracking technology provides insights into sleep architecture, though professional polysomnography remains the gold standard for diagnosis sleep disorders. Cognitive behavioral therapy for insomnia (CBT-I) proves more effective long-term than sleep medications for chronic sleep issues. Strategic napping (10-20 minutes) can boost alertness without sleep inertia, while consistent wake times anchor circadian rhythms more effectively than consistent bedtimes. Understanding these principles enables individuals to optimize their sleep for better health and performance.",
                getRandomTimezone()));

        allPosts.add(createPost(12L, "Digital Identity Systems",
                "Digital identity systems are evolving from simple username-password combinations to sophisticated biometric and blockchain-based solutions. Self-sovereign identity (SSI) models give individuals control over their personal data, allowing selective disclosure of information without relying on central authorities. Biometric authentication using fingerprints, facial recognition, and iris scans provides greater security than traditional passwords, which are vulnerable to phishing and data breaches. Decentralized identifiers (DIDs) on blockchain networks create tamper-proof identity records that individuals can use across multiple services. Governments are implementing national digital identity programs, with India's Aadhaar system covering over 1.3 billion people. The European Digital Identity Wallet aims to provide all EU citizens with secure access to public and private services. However, digital identity systems raise privacy concerns and risks of exclusion for populations without access to required technology. Balancing security, privacy, and accessibility remains the central challenge in developing digital identity solutions that serve both individuals and institutions.",
                getRandomTimezone()));

        allPosts.add(createPost(45L, "Robotic Process Automation",
                "Robotic Process Automation (RPA) uses software robots to automate repetitive, rule-based tasks traditionally performed by humans. These digital workers can process transactions, manipulate data, trigger responses, and communicate with other systems 24/7 without human intervention. RPA differs from traditional automation by mimicking human actions at the user interface level rather than integrating at the database or API level. Common applications include data entry, invoice processing, customer onboarding, and report generation. The technology delivers rapid ROI, with some organizations achieving payback in less than 12 months through reduced labor costs and improved accuracy. RPA platforms increasingly incorporate artificial intelligence for handling semi-structured data and making simple decisions. While RPA creates efficiency, it also necessitates workforce reskilling as routine tasks become automated. Successful implementation requires careful process selection, change management, and governance to ensure bots operate effectively alongside human employees in hybrid digital workplaces.",
                getRandomTimezone()));

        allPosts.add(createPost(23L, "Climate Resilience Engineering",
                "Climate resilience engineering involves designing and retrofitting infrastructure to withstand increasingly severe climate impacts. This includes elevating coastal structures, strengthening buildings against high winds, and designing drainage systems for more intense precipitation. Materials science innovations produce concrete that self-heals cracks using embedded bacteria and asphalt that reduces urban heat island effect. Nature-based solutions like wetland restoration and living shorelines provide cost-effective protection while enhancing biodiversity. Critical infrastructure redundancy ensures systems like power grids and water treatment facilities have backup capacity during extreme events. The engineering community is developing new design standards that incorporate climate projections rather than historical data alone. Retrofitting existing infrastructure presents particular challenges, requiring innovative approaches like deployable flood barriers and adaptive reuse of structures for new climate realities. Investment in climate resilience delivers significant economic benefits, with studies showing $6 in future disaster costs saved for every $1 spent on pre-disaster mitigation.",
                getRandomTimezone()));

        allPosts.add(createPost(67L, "The Future of Food Delivery",
                "Food delivery technology is evolving beyond simple app-based ordering to integrated ecosystems incorporating robotics, AI, and advanced logistics. Autonomous delivery vehicles including ground robots and drones are being tested for contactless delivery with lower operational costs. Dark kitchens (or ghost kitchens) optimize food preparation for delivery-only operations, located in low-rent areas closer to customer concentrations. AI algorithms predict order volumes, optimize delivery routes in real-time, and personalize menu recommendations. Subscription models offer unlimited delivery for monthly fees, changing customer economics and ordering behavior. Sustainability initiatives address packaging waste through reusable container programs and compostable materials. The integration of grocery and meal kit delivery creates one-stop platforms for all food needs. As the market consolidates, profitability challenges persist despite massive growth, leading to experimentation with various business models. The future points toward fully automated food preparation and delivery systems that minimize human involvement while maximizing convenience and efficiency.",
                getRandomTimezone()));
        allPosts.add(createPost(34L, "Wearable Health Technology",
                "Wearable health devices have evolved from simple step counters to sophisticated medical-grade monitors that track vital signs, detect abnormalities, and even predict health events. Modern smartwatches can perform electrocardiograms, measure blood oxygen levels, and monitor sleep patterns with clinical accuracy. Continuous glucose monitors revolutionize diabetes management by providing real-time blood sugar readings without finger pricks. Smart patches track medication adherence and physiological markers, transmitting data to healthcare providers. Advanced wearables now incorporate AI algorithms that can detect atrial fibrillation, sleep apnea, and early signs of infection. The integration of these devices with electronic health records creates comprehensive health profiles that enable proactive rather than reactive care. Privacy and security remain critical concerns as sensitive health data flows through connected ecosystems. Regulatory bodies are establishing frameworks to ensure device accuracy and data protection. As technology advances, wearables are becoming essential tools for personalized healthcare, remote patient monitoring, and early disease detection, potentially reducing healthcare costs while improving outcomes.",
                getRandomTimezone()));

        allPosts.add(createPost(78L, "Green Building Certification",
                "Green building certification systems like LEED, BREEAM, and WELL establish standards for environmentally responsible and healthy building design. LEED certification evaluates buildings across multiple categories including energy efficiency, water conservation, material selection, and indoor environmental quality. The WELL Building Standard focuses specifically on human health and wellness, addressing air, water, nourishment, light, fitness, comfort, and mind. Passive house design principles achieve exceptional energy efficiency through superior insulation, airtight construction, and heat recovery ventilation. Living Building Challenge represents the most rigorous standard, requiring net-zero energy and water, along with non-toxic materials. Green roofs and walls provide insulation, reduce urban heat island effect, and manage stormwater. The business case for green buildings has strengthened with studies showing higher occupancy rates, increased rental premiums, and improved worker productivity. As climate concerns grow, green certification is becoming a baseline expectation rather than a luxury feature in commercial and residential construction.",
                getRandomTimezone()));

        allPosts.add(createPost(56L, "The Ethics of AI Decision Making",
                "As artificial intelligence systems make increasingly important decisions, ethical frameworks become essential to ensure fair, transparent, and accountable outcomes. Algorithmic bias represents a significant challenge, with AI systems sometimes perpetuating or amplifying existing societal prejudices in areas like hiring, lending, and criminal justice. The 'black box' problem—where AI decisions cannot be easily explained—creates accountability gaps in critical applications. Various frameworks have emerged to address these concerns, including the EU's Ethics Guidelines for Trustworthy AI and principles of explainable AI (XAI). Techniques like adversarial debiasing and fairness constraints help mitigate bias, while model interpretability methods provide insight into AI reasoning. Human-in-the-loop systems maintain human oversight for high-stakes decisions. Regulatory developments are establishing requirements for AI auditing and impact assessments. The field of AI ethics is evolving rapidly, with ongoing debates about responsibility allocation, privacy preservation, and the appropriate boundaries for automated decision-making across different domains from healthcare to autonomous vehicles.",
                getRandomTimezone()));

        allPosts.add(createPost(91L, "Space Debris Management",
                "Orbital debris poses increasing threats to satellites, space stations, and future space missions. More than 34,000 objects larger than 10 cm and millions of smaller pieces currently orbit Earth, traveling at speeds up to 28,000 km/h. Collisions with even small debris can cause catastrophic damage to spacecraft. Active debris removal technologies being developed include robotic arms, nets, harpoons, and laser systems that can capture or deorbit defunct satellites and rocket stages. The European Space Agency's ClearSpace mission will demonstrate debris capture and removal. Satellite operators are increasingly designing spacecraft with end-of-life disposal capabilities, such as propulsion systems for moving to graveyard orbits or ensuring atmospheric reentry. International guidelines recommend deorbiting satellites within 25 years of mission completion, but compliance remains inconsistent. As commercial satellite constellations expand, collision avoidance systems become essential for managing traffic in crowded orbital regions. Addressing the space debris problem requires international cooperation, technological innovation, and regulatory frameworks to ensure sustainable access to space.",
                getRandomTimezone()));

        allPosts.add(createPost(12L, "Personalized Learning Algorithms",
                "Adaptive learning systems use artificial intelligence to customize educational content and pacing to individual student needs. These platforms continuously assess student performance, identifying knowledge gaps and adjusting lesson difficulty in real-time. Machine learning algorithms analyze patterns in student interactions to predict which concepts will be challenging and preemptively provide additional support. Natural language processing enables intelligent tutoring systems that can understand student questions and provide personalized explanations. Gamification elements maintain engagement while collecting rich data on learning behaviors. Research shows that personalized learning can improve student outcomes by 30% or more compared to traditional one-size-fits-all approaches. The technology also provides teachers with detailed analytics about class and individual student progress, enabling more targeted instruction. Challenges include ensuring algorithmic fairness, protecting student data privacy, and addressing the digital divide. As evidence of effectiveness grows, personalized learning platforms are being adopted in K-12 education, higher education, and corporate training environments, potentially transforming how knowledge is acquired and assessed.",
                getRandomTimezone()));

        allPosts.add(createPost(45L, "Circular Economy Models",
                "The circular economy represents a systemic shift from the traditional linear 'take-make-waste' model to one that eliminates waste and circulates resources. Core principles include designing out waste and pollution, keeping products and materials in use, and regenerating natural systems. Product-as-a-service business models shift focus from ownership to performance, incentivizing durability and reparability. Material innovation enables safe cycling of technical nutrients while supporting regeneration of biological nutrients. Digital platforms facilitate sharing, repair, and redistribution of underutilized assets. Industrial symbiosis creates networks where one company's waste becomes another's raw material. The economic opportunity is substantial—transitioning to a circular economy could generate $4.5 trillion in economic benefits by 2030. Companies adopting circular principles often discover new revenue streams while reducing material costs and environmental impact. Policy measures including extended producer responsibility, green public procurement, and tax reforms support the transition. While challenges remain in scaling circular solutions, the model offers a pathway to decouple economic growth from resource consumption.",
                getRandomTimezone()));

        allPosts.add(createPost(23L, "Augmented Reality in Retail",
                "Augmented reality is transforming retail by blending digital information with physical shopping experiences. Virtual try-on solutions allow customers to see how clothing, accessories, or makeup will look on them without physical samples. Furniture retailers use AR to let customers visualize how products will fit and look in their homes. Interactive product information appears when customers point their smartphones at items, showing specifications, reviews, and alternative options. In-store navigation guides shoppers to desired products while highlighting promotions along their path. Virtual stores enable browsing and purchasing from anywhere while maintaining the visual appeal of physical retail. AR mirrors in fitting rooms suggest complementary items and different sizes or colors without leaving the room. The technology reduces returns by helping customers make more informed purchases and increases conversion rates by enhancing engagement. As AR hardware becomes more accessible and software more sophisticated, these immersive shopping experiences are moving from novelty to expectation, reshaping both online and brick-and-mortar retail.",
                getRandomTimezone()));

        allPosts.add(createPost(67L, "Microbiome Research Breakthroughs",
                "Research into the human microbiome—the trillions of microorganisms living in and on our bodies—is revealing profound connections between microbial communities and human health. The gut-brain axis demonstrates how gut microbes influence neurotransmitter production, mood, and cognitive function. Fecal microbiota transplantation has shown remarkable success treating recurrent C. difficile infections, leading to exploration of microbiome-based therapies for other conditions. Personalized nutrition based on individual microbiome composition may optimize metabolic health more effectively than generic dietary advice. The skin microbiome affects barrier function, immunity, and conditions like eczema and acne. Vaginal microbiome composition influences reproductive health and pregnancy outcomes. Advanced sequencing technologies and computational tools enable detailed mapping of microbial ecosystems and their functions. Microbiome-based diagnostics are emerging for early detection of diseases including colorectal cancer and inflammatory bowel disease. While the field holds tremendous promise, challenges remain in establishing causation beyond correlation and developing reliable interventions that produce predictable outcomes across diverse individuals.",
                getRandomTimezone()));

        allPosts.add(createPost(34L, "Smart Grid Technology",
                "Smart grids represent the digital transformation of electrical infrastructure, incorporating sensors, communication networks, and automation to optimize electricity generation, distribution, and consumption. Advanced metering infrastructure provides real-time data on energy use, enabling dynamic pricing and demand response programs. Distribution automation systems quickly detect and isolate faults, restoring power to unaffected areas within seconds. Grid-scale energy storage helps balance supply and demand, integrating intermittent renewable sources. Microgrid controllers manage local generation and consumption, maintaining power during wider grid outages. Cybersecurity measures protect critical infrastructure from increasingly sophisticated threats. Consumer engagement platforms empower customers to manage energy use and costs through web portals and mobile apps. The business case for smart grid investments includes reduced operational costs, improved reliability, and deferred capital expenditures. As electricity demand grows and renewable generation expands, smart grid technologies become essential for maintaining grid stability while supporting climate goals. The transition requires careful planning, regulatory support, and public-private collaboration to realize the full benefits of a digitalized energy system.",
                getRandomTimezone()));

        allPosts.add(createPost(78L, "The Psychology of Social Networks",
                "Social media platforms are designed around psychological principles that maximize engagement, often with unintended consequences for mental health and social dynamics. Variable reward schedules—the same mechanism that makes slot machines addictive—keep users checking for notifications and updates. Social validation through likes and shares activates the brain's reward system, creating reinforcement loops. Fear of missing out (FOMO) drives compulsive checking behaviors. Algorithmic curation creates filter bubbles that reinforce existing beliefs and limit exposure to diverse perspectives. Comparison with carefully curated versions of others' lives can diminish self-esteem and life satisfaction. The performative nature of online identity can create authenticity crises and identity fragmentation. However, social networks also provide meaningful connection, social support, and access to information. Understanding these psychological mechanisms enables more mindful usage and platform design that prioritizes wellbeing over mere engagement. Digital literacy education helps users navigate social media environments more critically, while platform features like usage timers and chronological feeds offer tools for healthier engagement patterns.",
                getRandomTimezone()));

        allPosts.add(createPost(56L, "Hydrogen Fuel Cell Development",
                "Hydrogen fuel cells offer a clean energy solution for transportation and power generation, producing only water and heat as byproducts. Proton exchange membrane fuel cells are particularly suited for vehicles, offering quick refueling and ranges comparable to conventional vehicles. Solid oxide fuel cells operate at higher temperatures, achieving greater efficiency for stationary power generation. The hydrogen economy encompasses production, storage, distribution, and utilization, with 'colors' indicating production methods: green hydrogen from renewable electricity, blue from natural gas with carbon capture, and gray from fossil fuels without emissions mitigation. Cost reductions in electrolyzers and renewable energy are making green hydrogen increasingly economically viable. Storage challenges are being addressed through advanced compression techniques, liquid organic hydrogen carriers, and metal hydrides. Fuel cell electric vehicles are gaining traction in commercial fleets where hydrogen's quick refueling provides operational advantages over battery electric vehicles. While infrastructure development remains a barrier, national hydrogen strategies and increasing private investment are accelerating the transition toward a hydrogen economy that complements electrification in decarbonizing hard-to-abate sectors.",
                getRandomTimezone()));

        allPosts.add(createPost(91L, "Digital Twin Technology",
                "Digital twins are virtual replicas of physical assets, processes, or systems that update and change as their physical counterparts change. Enabled by IoT sensors, cloud computing, and machine learning, digital twins create living simulation models that support decision-making across product lifecycles. In manufacturing, digital twins optimize production lines by simulating changes before physical implementation. Cities use urban digital twins to model traffic patterns, emergency response scenarios, and infrastructure development. Healthcare applications include personalized organ models for surgical planning and hospital operation optimization. The aerospace industry employs digital twins to monitor aircraft health, predict maintenance needs, and extend operational life. Energy companies create digital twins of power plants and grids to improve efficiency and reliability. The technology enables predictive maintenance, reducing downtime and extending asset lifecycles. As computational power increases and sensor networks expand, digital twins are becoming more sophisticated and accessible. The global digital twin market is projected to exceed $50 billion by 2026, driven by adoption across industries seeking to improve efficiency, reduce costs, and enhance decision-making through simulation and prediction.",
                getRandomTimezone()));

        allPosts.add(createPost(12L, "Regenerative Agriculture Methods",
                "Regenerative agriculture comprises farming and grazing practices that reverse climate change by rebuilding soil organic matter and restoring degraded soil biodiversity. Key practices include no-till farming, which minimizes soil disturbance; cover cropping to protect bare soil; diverse crop rotations that break pest cycles; and managed grazing that mimics natural herd movements. These approaches increase soil carbon sequestration, improve water retention, and enhance nutrient cycling. Agroforestry integrates trees with crops or livestock, creating synergistic relationships that benefit both components. Compost application and biochar incorporation further enhance soil health. Beyond environmental benefits, regenerative practices often improve farm profitability through reduced input costs and resilience to extreme weather. Measurement techniques including soil carbon monitoring and satellite imagery help quantify outcomes and verify carbon sequestration for emerging ecosystem service markets. While transitioning from conventional methods requires knowledge development and initial investment, the multiple benefits of regenerative agriculture—from climate mitigation to improved food nutrition—are driving growing adoption by farmers and support from food companies and policymakers.",
                getRandomTimezone()));

        allPosts.add(createPost(45L, "Voice Interface Design",
                "Voice user interface (VUI) design creates natural, intuitive interactions between humans and devices using speech. Unlike visual interfaces, VUIs are invisible and transient, requiring different design principles. Effective VUI design considers conversation flow, error handling, personality, and context. Natural language processing enables systems to understand user intent from varied phrasing, while natural language generation creates human-like responses. Personality design establishes appropriate character traits that match the application context—a banking assistant might be formal and precise, while a storytelling app could be playful. Multimodal interfaces combine voice with visual or haptic feedback to create more robust experiences. Privacy considerations are paramount as voice interactions often occur in personal spaces and may be recorded. The proliferation of smart speakers, voice assistants in vehicles, and voice-controlled appliances is driving demand for well-designed voice interfaces. As voice technology improves in accuracy and capability, VUI design is evolving from simple command-response interactions toward more natural conversations that understand context, manage complex tasks, and adapt to individual user preferences and speaking styles.",
                getRandomTimezone()));

        allPosts.add(createPost(23L, "Carbon Capture Innovations",
                "Carbon capture, utilization, and storage (CCUS) technologies are essential for achieving climate goals, particularly for decarbonizing hard-to-abate industrial sectors and achieving negative emissions. Point-source capture systems installed at power plants and industrial facilities prevent CO2 from entering the atmosphere. Direct air capture technology removes CO2 already in the atmosphere, potentially enabling negative emissions. Emerging capture methods include electrochemical approaches that reduce energy requirements, enzyme-based systems inspired by biological processes, and metal-organic frameworks with high selectivity for CO2. Utilization pathways transform captured carbon into valuable products including concrete aggregates, fuels, chemicals, and even diamonds. Storage options include deep geological formations, mineral carbonation that permanently binds CO2 in stable minerals, and enhanced oil recovery with associated storage. The cost of carbon capture has decreased significantly but remains a barrier to widespread deployment. Policy mechanisms including carbon pricing, tax credits, and procurement standards are driving investment and innovation. As scale increases and costs continue to decline, CCUS is poised to play a crucial role in the portfolio of climate solutions.",
                getRandomTimezone()));
        allPosts.add(createPost(67L, "The Future of Digital Payments",
                "Digital payment systems are evolving beyond credit cards and mobile wallets toward seamless, embedded financial experiences. Central bank digital currencies (CBDCs) are being developed by over 100 countries, potentially transforming monetary policy and financial inclusion. Cryptocurrencies and stablecoins offer borderless transactions but face regulatory scrutiny and volatility challenges. Biometric payments using fingerprints, facial recognition, or vein patterns are eliminating the need for physical cards or devices. Buy now, pay later (BNPL) services are reshaping consumer credit, particularly among younger demographics who avoid traditional credit cards. Embedded finance integrates payment capabilities directly into non-financial platforms, from social media apps to vehicle dashboards. Real-time payment systems are becoming the global standard, with settlement times reduced from days to seconds. Security innovations include tokenization, which replaces sensitive data with unique tokens, and behavioral biometrics that continuously authenticate users based on typing patterns and device handling. As digital payments become increasingly invisible and integrated into daily life, the focus shifts toward security, interoperability, and ensuring equitable access across all socioeconomic groups.",
                getRandomTimezone()));

        allPosts.add(createPost(34L, "Smart Water Management",
                "Advanced water management systems leverage IoT sensors, AI analytics, and automation to address global water scarcity challenges. Smart meters provide real-time consumption data, enabling leak detection and demand-based pricing. Soil moisture sensors optimize agricultural irrigation, reducing water usage by up to 30% while maintaining crop yields. AI-powered systems analyze weather patterns, evaporation rates, and plant needs to create precise irrigation schedules. Advanced water quality monitoring uses spectral analysis and machine learning to detect contaminants in real-time, far faster than traditional lab testing. Distribution network optimization minimizes pumping energy and reduces non-revenue water losses from leaks and theft. Greywater and rainwater harvesting systems, integrated with smart controls, maximize water reuse for non-potable applications. Digital twin technology creates virtual models of water systems for simulation and optimization. These technologies are particularly crucial in drought-prone regions and rapidly urbanizing areas where water infrastructure struggles to keep pace with demand. The integration of smart water management represents a critical step toward water security in an increasingly water-stressed world.",
                getRandomTimezone()));

        allPosts.add(createPost(78L, "Neurotechnology Interfaces",
                "Brain-computer interfaces (BCIs) are advancing from medical applications to potential consumer products, creating direct communication pathways between the brain and external devices. Non-invasive BCIs using EEG headsets can control prosthetics, play games, and enable communication for paralyzed individuals. Invasive neural implants show promise for restoring vision, movement, and memory in patients with neurological conditions. Companies are developing high-bandwidth interfaces that could eventually allow thought-based control of computers and direct brain-to-brain communication. Neurotechnology is also advancing our understanding of brain function, with applications in treating depression, PTSD, and addiction through targeted neuromodulation. Ethical considerations are paramount as these technologies raise questions about cognitive liberty, mental privacy, and the potential for enhancement versus therapy. Regulatory frameworks are evolving to address safety concerns and establish boundaries for neurotechnology applications. While fully developed consumer brain interfaces remain years away, rapid progress in neuroscience, materials science, and AI is accelerating the development of technologies that could fundamentally change how humans interact with computers and each other.",
                getRandomTimezone()));

        allPosts.add(createPost(56L, "Sustainable Tourism Development",
                "Sustainable tourism seeks to balance the economic benefits of travel with environmental protection and community wellbeing. Regenerative tourism goes beyond minimizing harm to actively improving destinations through visitor activities. Certification programs like Green Key and EarthCheck establish standards for eco-friendly accommodations and tour operators. Overtourism management strategies include visitor caps, timed entry systems, and dispersion techniques that redirect travelers to less-crowded areas. Community-based tourism ensures local residents benefit economically while maintaining cultural integrity. Carbon offset programs specifically designed for travel help mitigate aviation emissions, though reduction remains preferable to offsetting. Wildlife tourism standards protect animals from exploitation while supporting conservation efforts. Digital nomad visas create longer-term economic benefits for host communities compared to short-term mass tourism. The pandemic-induced travel pause provided an opportunity to reconsider tourism models, with many destinations implementing more sustainable approaches as travel resumes. The future of tourism lies in quality-over-quantity experiences that benefit travelers, host communities, and the environment simultaneously.",
                getRandomTimezone()));

        allPosts.add(createPost(91L, "The Future of Quantum Internet",
                "Quantum internet represents the next frontier in secure communications, leveraging quantum entanglement and superposition to create unhackable networks. Unlike conventional internet that transmits bits (0s and 1s), quantum internet uses qubits that can exist in multiple states simultaneously. Quantum key distribution (QKD) enables perfectly secure communication—any eavesdropping attempt inevitably disturbs the quantum states, alerting the legitimate users. Quantum teleportation transfers quantum states between particles over distance without physical transmission. Early quantum networks already exist connecting research institutions, with plans for metropolitan-scale networks underway. The technology promises not only unprecedented security but also enables distributed quantum computing where multiple quantum processors collaborate on problems. Challenges include maintaining quantum coherence over long distances, requiring quantum repeaters to extend range. While a global quantum internet remains years away, rapid progress is being made in developing the necessary components including quantum memories, repeaters, and interfaces. This emerging infrastructure will eventually support applications from secure voting and banking to connecting future quantum computers into a world-wide web of quantum information.",
                getRandomTimezone()));

        allPosts.add(createPost(12L, "Sustainable Water Management",
                "Sustainable water management addresses the growing global water crisis through integrated approaches that balance human needs with ecosystem protection. Watershed-based management considers entire river basins as interconnected systems, coordinating across jurisdictional boundaries. Green infrastructure like permeable pavements, rain gardens, and constructed wetlands manages stormwater naturally while replenishing aquifers. Water reuse systems treat wastewater to appropriate standards for various non-potable applications, reducing demand on freshwater sources. Demand management strategies include water-efficient appliances, xeriscaping with drought-tolerant plants, and public education campaigns. Agricultural water management employs precision irrigation, drought-resistant crops, and soil moisture conservation techniques. Governance models that include diverse stakeholders—from farmers to urban residents to environmental groups—prove more effective than top-down approaches. Climate resilience planning anticipates how changing precipitation patterns will affect water availability and infrastructure needs. Successful sustainable water management requires combining technological solutions with policy reforms, economic instruments, and community engagement to create systems that can meet human needs while protecting the aquatic ecosystems that sustain all life.",
                getRandomTimezone()));

        allPosts.add(createPost(45L, "The Psychology of Decision Making",
                "Decision science reveals that human choices are influenced by cognitive biases, emotional states, and environmental factors rather than pure rationality. The dual-process theory distinguishes between fast, intuitive System 1 thinking and slow, analytical System 2 thinking. Common biases include confirmation bias (favoring information that confirms existing beliefs), anchoring (over-relying on initial information), and loss aversion (feeling losses more strongly than equivalent gains). Choice architecture—how options are presented—significantly impacts decisions, as demonstrated by the powerful effects of default options. Decision fatigue describes how quality deteriorates after repeated decision-making. Emotional influences include the affect heuristic, where current emotions color judgments, and the optimism bias, which underestimates negative outcomes. Improved decision-making strategies include precommitment devices, considering alternative explanations, and using checklists to reduce cognitive load. Understanding these psychological mechanisms enables better personal decisions and more effective organizational processes. Applications range from improving medical diagnoses and financial planning to designing public policies that help people make choices aligned with their long-term interests.",
                getRandomTimezone()));

        allPosts.add(createPost(23L, "Ocean Exploration Technologies",
                "Advanced technologies are revealing the mysteries of Earth's final frontier—the deep ocean. Remotely operated vehicles (ROVs) equipped with high-definition cameras, sampling tools, and sensors explore depths up to 6,000 meters, discovering new species and underwater landscapes. Autonomous underwater vehicles (AUVs) map large areas of seafloor with sonar and photographic systems, operating independently for days or weeks. Advanced sampling systems collect water, sediment, and biological specimens while preserving their delicate chemical and biological integrity. Genomic sequencing tools identify species from environmental DNA (eDNA) without direct observation. Sensor networks on seafloor observatories provide continuous monitoring of chemical, physical, and biological processes. Communications technologies including acoustic modems and laser systems transmit data through water. These technologies have revealed hydrothermal vent ecosystems, underwater volcanoes, and deep-sea coral gardens, fundamentally changing our understanding of marine biodiversity and ocean processes. The data collected informs conservation efforts, reveals potential biomedical compounds from marine organisms, and helps understand how climate change affects ocean systems. As technology advances, ocean exploration continues to yield discoveries that reshape our understanding of life on Earth.",
                getRandomTimezone()));

        allPosts.add(createPost(67L, "Advanced Materials Science",
                "Materials science is engineering substances with unprecedented properties through nanoscale manipulation and bio-inspired design. Self-healing materials incorporate microcapsules of healing agent that rupture when damaged, automatically repairing cracks. Programmable materials change shape, stiffness, or other properties in response to environmental triggers like temperature, light, or magnetic fields. Aerogels with nanoscale porous structures offer exceptional thermal insulation with minimal weight. Metamaterials exhibit properties not found in nature, including negative refractive index for invisibility cloaking and acoustic insulation that blocks sound while allowing air passage. Biomimetic materials replicate natural designs like the strength-to-weight ratio of spider silk or the self-cleaning properties of lotus leaves. 2D materials like graphene demonstrate extraordinary electrical, thermal, and mechanical characteristics. Smart textiles integrate sensors, phase-change materials for temperature regulation, and conductive fibers for data transmission. These advanced materials enable breakthroughs across industries—from aerospace to medicine to energy—creating products that are stronger, lighter, more efficient, and more responsive to their environments and users.",
                getRandomTimezone()));

        allPosts.add(createPost(34L, "The Evolution of Social Entrepreneurship",
                "Social entrepreneurship has matured from a niche concept to a mainstream approach for addressing societal challenges through market-based solutions. Unlike traditional non-profits, social enterprises generate revenue while pursuing social or environmental missions, creating sustainable funding models. Impact investing has grown into a $700 billion market, with investors seeking both financial returns and measurable social benefits. Certification systems like B Corp provide standards for social and environmental performance, accountability, and transparency. Hybrid legal structures including benefit corporations and social purpose corporations embed mission protection into corporate governance. Successful social enterprises demonstrate that addressing social problems can be compatible with business viability, from providing clean water in developing countries to creating employment opportunities for marginalized populations. Measurement frameworks like IRIS+ standardize impact assessment, enabling comparison across organizations and sectors. The field continues to evolve with trends including platform cooperatives owned by their users, corporate social innovation initiatives within traditional companies, and focus on systems change rather than isolated interventions. Social entrepreneurship represents a promising pathway for mobilizing entrepreneurial energy toward creating a more equitable and sustainable world.",
                getRandomTimezone()));
    }

    private void createComments() {
        System.out.println("Creating Random Comments...");

        createComment(23L, 5L, "This is an incredibly thorough analysis! I particularly appreciated the section about future trends. The data you presented about market adoption rates was especially eye-opening. Have you considered how recent policy changes might affect these projections?", getRandomTimezone());

        createComment(67L, 12L, "As someone working in this field for over a decade, I can confirm many of your observations. The point about scalability challenges is spot-on. We faced similar issues in our implementation last year. Would love to hear more about your thoughts on potential solutions.", getRandomTimezone());

        createComment(45L, 8L, "The historical context you provided really helped frame the current situation. I hadn't considered the parallels with previous technological revolutions. The comparison to the industrial revolution was particularly insightful. Do you think we're underestimating the social implications?", getRandomTimezone());

        createComment(12L, 25L, "Excellent research and presentation! The case studies from different regions provided valuable comparative perspectives. I noticed the Asian market analysis was particularly strong. Have you looked at how cultural factors might influence adoption rates?", getRandomTimezone());

        createComment(89L, 3L, "This post deserves to be widely shared. The way you've broken down complex concepts into understandable segments is masterful. The practical recommendations at the end are especially valuable for practitioners. Thank you for this contribution!", getRandomTimezone());

        createComment(34L, 17L, "I have some experience with this topic from the implementation side, and your assessment aligns with what we've observed. However, I'd challenge the assumption about cost-effectiveness in the medium term. Our data suggests different conclusions.", getRandomTimezone());

        createComment(78L, 42L, "The interdisciplinary approach you've taken here is refreshing. Connecting technological trends with sociological impacts provides a much-needed holistic perspective. The bibliography is impressive - several sources I hadn't encountered before.", getRandomTimezone());

        createComment(56L, 31L, "As a beginner in this field, I found this incredibly accessible while still being comprehensive. The examples and analogies really helped me grasp the core concepts. Looking forward to reading more of your work!", getRandomTimezone());

        createComment(91L, 7L, "This addresses many of the questions our team has been discussing lately. The security considerations section was particularly timely given recent developments. Would you be open to collaborating on a follow-up piece?", getRandomTimezone());

        createComment(15L, 49L, "The international perspective is what sets this analysis apart. Too often these discussions are US-centric, but you've done an excellent job of representing global diversity. The emerging markets analysis was especially valuable.", getRandomTimezone());
    }

    private void createLikes() {
        System.out.println("Creating Random Likes...");

        // Create 100 random likes with hardcoded values
        likePost(5L, 23L, getRandomTimezone());
        likePost(12L, 67L, getRandomTimezone());
        likePost(8L, 45L, getRandomTimezone());
        likePost(25L, 12L, getRandomTimezone());
        likePost(3L, 89L, getRandomTimezone());
        likePost(17L, 34L, getRandomTimezone());
        likePost(42L, 78L, getRandomTimezone());
        likePost(31L, 56L, getRandomTimezone());
        likePost(7L, 91L, getRandomTimezone());
        likePost(49L, 15L, getRandomTimezone());

        likePost(18L, 28L, getRandomTimezone());
        likePost(33L, 42L, getRandomTimezone());
        likePost(9L, 67L, getRandomTimezone());
        likePost(44L, 19L, getRandomTimezone());
        likePost(21L, 55L, getRandomTimezone());
        likePost(37L, 83L, getRandomTimezone());
        likePost(14L, 26L, getRandomTimezone());
        likePost(29L, 71L, getRandomTimezone());
        likePost(6L, 38L, getRandomTimezone());
        likePost(48L, 94L, getRandomTimezone());

        likePost(22L, 63L, getRandomTimezone());
        likePost(41L, 17L, getRandomTimezone());
        likePost(11L, 79L, getRandomTimezone());
        likePost(35L, 52L, getRandomTimezone());
        likePost(27L, 46L, getRandomTimezone());
        likePost(19L, 88L, getRandomTimezone());
        likePost(53L, 31L, getRandomTimezone());
        likePost(16L, 74L, getRandomTimezone());
        likePost(39L, 59L, getRandomTimezone());
        likePost(64L, 13L, getRandomTimezone());

        likePost(72L, 47L, getRandomTimezone());
        likePost(25L, 82L, getRandomTimezone());
        likePost(58L, 29L, getRandomTimezone());
        likePost(36L, 65L, getRandomTimezone());
        likePost(43L, 21L, getRandomTimezone());
        likePost(68L, 54L, getRandomTimezone());
        likePost(15L, 77L, getRandomTimezone());
        likePost(49L, 32L, getRandomTimezone());
        likePost(26L, 69L, getRandomTimezone());
        likePost(57L, 44L, getRandomTimezone());

        likePost(81L, 18L, getRandomTimezone());
        likePost(34L, 73L, getRandomTimezone());
        likePost(62L, 27L, getRandomTimezone());
        likePost(47L, 86L, getRandomTimezone());
        likePost(23L, 51L, getRandomTimezone());
        likePost(75L, 34L, getRandomTimezone());
        likePost(38L, 62L, getRandomTimezone());
        likePost(52L, 95L, getRandomTimezone());
        likePost(19L, 41L, getRandomTimezone());
        likePost(66L, 78L, getRandomTimezone());

        likePost(28L, 53L, getRandomTimezone());
        likePost(71L, 16L, getRandomTimezone());
        likePost(45L, 89L, getRandomTimezone());
        likePost(32L, 64L, getRandomTimezone());
        likePost(84L, 37L, getRandomTimezone());
        likePost(57L, 22L, getRandomTimezone());
        likePost(39L, 75L, getRandomTimezone());
        likePost(63L, 48L, getRandomTimezone());
        likePost(24L, 91L, getRandomTimezone());
        likePost(76L, 35L, getRandomTimezone());

        likePost(41L, 68L, getRandomTimezone());
        likePost(58L, 23L, getRandomTimezone());
        likePost(33L, 79L, getRandomTimezone());
        likePost(67L, 42L, getRandomTimezone());
        likePost(49L, 85L, getRandomTimezone());
        likePost(26L, 57L, getRandomTimezone());
        likePost(72L, 31L, getRandomTimezone());
        likePost(18L, 74L, getRandomTimezone());
        likePost(54L, 19L, getRandomTimezone());
        likePost(87L, 46L, getRandomTimezone());

        likePost(29L, 63L, getRandomTimezone());
        likePost(61L, 28L, getRandomTimezone());
        likePost(44L, 82L, getRandomTimezone());
        likePost(78L, 37L, getRandomTimezone());
        likePost(35L, 69L, getRandomTimezone());
        likePost(52L, 24L, getRandomTimezone());
        likePost(83L, 59L, getRandomTimezone());
        likePost(27L, 76L, getRandomTimezone());
        likePost(64L, 41L, getRandomTimezone());
        likePost(92L, 55L, getRandomTimezone());

        likePost(38L, 87L, getRandomTimezone());
        likePost(56L, 32L, getRandomTimezone());
        likePost(73L, 47L, getRandomTimezone());
        likePost(21L, 65L, getRandomTimezone());
        likePost(48L, 18L, getRandomTimezone());
        likePost(79L, 53L, getRandomTimezone());
        likePost(34L, 88L, getRandomTimezone());
        likePost(62L, 29L, getRandomTimezone());
        likePost(43L, 77L, getRandomTimezone());
        likePost(85L, 36L, getRandomTimezone());

        likePost(31L, 72L, getRandomTimezone());
        likePost(67L, 44L, getRandomTimezone());
        likePost(52L, 81L, getRandomTimezone());
        likePost(76L, 39L, getRandomTimezone());
        likePost(28L, 66L, getRandomTimezone());
        likePost(59L, 25L, getRandomTimezone());
        likePost(83L, 58L, getRandomTimezone());
        likePost(46L, 93L, getRandomTimezone());
        likePost(37L, 71L, getRandomTimezone());
        likePost(69L, 34L, getRandomTimezone());
    }

    private void createRelationships() {
        System.out.println("Creating Relationships...");


        followUser(5L, 23L);   // User 5 liked Post 23 (owner: 23) -> follow owner
        followUser(12L, 67L);  // User 12 liked Post 67 (owner: 67) -> follow owner
        followUser(8L, 45L);   // User 8 liked Post 45 (owner: 45) -> follow owner
        followUser(25L, 12L);  // User 25 liked Post 12 (owner: 12) -> follow owner
        followUser(3L, 89L);   // User 3 liked Post 89 (owner: 89) -> follow owner
        followUser(17L, 34L);  // User 17 liked Post 34 (owner: 34) -> follow owner
        followUser(42L, 78L);  // User 42 liked Post 78 (owner: 78) -> follow owner
        followUser(31L, 56L);  // User 31 liked Post 56 (owner: 56) -> follow owner
        followUser(7L, 91L);   // User 7 liked Post 91 (owner: 91) -> follow owner
        followUser(49L, 15L);  // User 49 liked Post 15 (owner: 15) -> follow owner

        // Users who commented on posts now follow the post owners
        followUser(23L, 5L);   // User 23 commented on Post 5 -> follow post owner
        followUser(67L, 12L);  // User 67 commented on Post 12 -> follow post owner
        followUser(45L, 8L);   // User 45 commented on Post 8 -> follow post owner
        followUser(12L, 25L);  // User 12 commented on Post 25 -> follow post owner
        followUser(89L, 3L);   // User 89 commented on Post 3 -> follow post owner
        followUser(34L, 17L);  // User 34 commented on Post 17 -> follow post owner
        followUser(78L, 42L);  // User 78 commented on Post 42 -> follow post owner
        followUser(56L, 31L);  // User 56 commented on Post 31 -> follow post owner
        followUser(91L, 7L);   // User 91 commented on Post 7 -> follow post owner
        followUser(15L, 49L);  // User 15 commented on Post 49 -> follow post owner

        followUser(18L, 23L);
        followUser(33L, 67L);
        followUser(9L, 45L);
        followUser(44L, 12L);
        followUser(21L, 89L);
        followUser(37L, 34L);
        followUser(14L, 78L);
        followUser(29L, 56L);
        followUser(6L, 91L);
        followUser(48L, 15L);

        followUser(22L, 5L);
        followUser(41L, 12L);
        followUser(11L, 8L);
        followUser(35L, 25L);
        followUser(27L, 3L);
        followUser(19L, 17L);
        followUser(53L, 42L);
        followUser(16L, 31L);
        followUser(39L, 7L);
        followUser(64L, 49L);

        followUser(72L, 23L);
        followUser(25L, 67L);
        followUser(58L, 45L);
        followUser(36L, 12L);
        followUser(43L, 89L);
        followUser(68L, 34L);
        followUser(15L, 78L);
        followUser(49L, 56L);
        followUser(26L, 91L);
        followUser(57L, 15L);

        followUser(81L, 5L);
        followUser(34L, 12L);
        followUser(62L, 8L);
        followUser(47L, 25L);
        followUser(23L, 3L);
        followUser(75L, 17L);
        followUser(38L, 42L);
        followUser(52L, 31L);
        followUser(19L, 7L);
        followUser(66L, 49L);

        System.out.println("Created relationships successfully!");
    }

    private User createUser(String username, String displayName, String email, String password, Timezone timezone) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, displayName, email, encodedPassword);
        userDAO.saveNewUser(user, timezone);
        return user;
    }

    private Post createPost(Long userId, String title, String body, Timezone timezone) {
        Post post = new Post(body, title, userId);
        Post savedPost = postDAO.saveNewPost(post, timezone);
        return savedPost;
    }

    private void createComment(Long userId, Long postId, String body, Timezone timezone) {
        Comment comment = new Comment(body, userId, postId);
        commentDAO.saveNewComment(comment, timezone);
    }

    private void likePost(Long postId, Long userId, Timezone timezone) {
        try {
            postDAO.likePost(postId, userId, timezone);
        } catch (Exception e) {
            // Ignore duplicate likes
        }
    }

    private void followUser(Long followerId, Long followingId) {
        try {
            userDAO.follow(followerId, followingId);
        } catch (Exception e) {
            // Ignore duplicate follows
        }
    }


    private Timezone getRandomTimezone() {
        return allTimezones[random.nextInt(allTimezones.length)];
    }

}

